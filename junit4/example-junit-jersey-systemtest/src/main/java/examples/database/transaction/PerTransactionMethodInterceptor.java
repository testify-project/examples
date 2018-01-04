/*
 * Copyright 2016-2017 Testify Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package examples.database.transaction;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.operation.OperationHandle;
import org.glassfish.hk2.extras.operation.OperationManager;
import org.jvnet.hk2.annotations.Service;

/**
 * A method interceptor that intercepts methods annotated with @Transactional. This interceptor
 * begins, commits, and rolls back the currently active entity manager.
 *
 * @author saden
 */
@Service
public class PerTransactionMethodInterceptor implements MethodInterceptor {

    private final OperationManager operationManager;
    private final EntityManager entityManager;

    @Inject
    PerTransactionMethodInterceptor(OperationManager operationManager,
            EntityManager entityManager) {
        this.operationManager = operationManager;
        this.entityManager = entityManager;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        OperationHandle<PerTransaction> handler = getOperationHandle();

        EntityTransaction tx = entityManager.getTransaction();

        if (tx == null || tx.isActive()) {
            return methodInvocation.proceed();
        }

        Object result;

        try {
            tx.begin();
            result = methodInvocation.proceed();
        } catch (Exception e) {
            doRollbackOrCommit(methodInvocation, e, tx);

            handler.closeOperation();

            throw e;
        }

        try {
            if (tx.isActive()) {
                tx.commit();
            }
        } finally {
            handler.closeOperation();
        }

        return result;
    }

    private synchronized OperationHandle<PerTransaction> getOperationHandle() {
        OperationHandle<PerTransaction> handler = operationManager.getCurrentOperation(
                PerTransactionImpl.INSTANCE);

        if (handler == null) {
            handler = operationManager.createAndStartOperation(PerTransactionImpl.INSTANCE);
        }

        return handler;

    }

    private void doRollbackOrCommit(MethodInvocation methodInvocation, Exception e,
            EntityTransaction tx) {
        if (tx.getRollbackOnly()) {
            tx.rollback();

            return;
        }

        Optional<Transactional> result = getTransactional(methodInvocation);

        if (result.isPresent()) {
            Transactional transactional = result.get();
            boolean commit = true;

            for (Class<? extends Exception> rollbackOn : transactional.rollbackOn()) {
                if (rollbackOn.isInstance(e)) {
                    commit = false;

                    for (Class<? extends Exception> exceptOn : transactional.dontRollbackOn()) {
                        if (exceptOn.isInstance(e)) {
                            commit = true;
                            break;
                        }
                    }

                    if (!commit) {
                        tx.rollback();
                    }

                    break;
                }
            }

            if (commit && tx.isActive()) {
                tx.commit();
            }
        }
    }

    private Optional<Transactional> getTransactional(MethodInvocation methodInvocation) {
        Transactional transactional;
        Method method = methodInvocation.getMethod();

        transactional = method.getAnnotation(Transactional.class);

        if (transactional == null) {
            Class<?> targetClass = methodInvocation.getThis().getClass();
            transactional = targetClass.getAnnotation(Transactional.class);
        }

        return Optional.ofNullable(transactional);
    }

}
