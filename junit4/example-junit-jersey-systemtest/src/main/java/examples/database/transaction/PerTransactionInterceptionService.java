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

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.annotations.Service;

/**
 * An HK2 interception service that configures the interception of methods annotated with
 * {@link Transactional} annotation.
 *
 * @author saden
 */
@Service
public class PerTransactionInterceptionService implements InterceptionService {

    private final MethodInterceptor interceptor;

    @Inject
    PerTransactionInterceptionService(PerTransactionMethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public Filter getDescriptorFilter() {
        return BuilderHelper.allFilter();
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        return getTransactional(method)
                .map(p -> asList(interceptor))
                .orElse(null);
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
        return null;
    }

    private Optional<Transactional> getTransactional(Method method) {
        Transactional transactional = method.getAnnotation(Transactional.class);

        if (transactional == null) {
            transactional = method.getDeclaringClass().getAnnotation(Transactional.class);
        }

        return ofNullable(transactional);
    }

}
