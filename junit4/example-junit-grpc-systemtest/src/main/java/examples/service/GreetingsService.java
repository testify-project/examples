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
package examples.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.google.inject.persist.Transactional;
import com.google.protobuf.Empty;

import example.grpc.GreetingsGrpc;
import example.grpc.common.GreetingId;
import example.grpc.common.GreetingRequest;
import example.grpc.common.GreetingResponse;
import examples.service.entity.GreetingEntity;
import io.grpc.stub.StreamObserver;

/**
 * TODO.
 *
 * @author saden
 */
@Transactional
public class GreetingsService extends GreetingsGrpc.GreetingsImplBase {

    private final EntityManager entityManager;

    @Inject
    GreetingsService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(GreetingRequest request, StreamObserver<GreetingId> responseObserver) {
        try {
            GreetingEntity entity = new GreetingEntity(request.getPhrase());
            entityManager.persist(entity);

            GreetingId greetingId = GreetingId.newBuilder()
                    .setId(entity.getId().toString())
                    .build();

            responseObserver.onNext(greetingId);
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void get(GreetingId request, StreamObserver<GreetingResponse> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            GreetingEntity entity = entityManager.getReference(GreetingEntity.class, id);

            GreetingResponse response = GreetingResponse.newBuilder()
                    .setId(entity.getId().toString())
                    .setPhrase(entity.getPhrase())
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void list(Empty request, StreamObserver<GreetingResponse> responseObserver) {
        try {
            List<GreetingEntity> result = entityManager
                    .createQuery("SELECT g FROM GreetingEntity g")
                    .getResultList();

            result.stream()
                    .map(entity -> GreetingResponse.newBuilder()
                            .setId(entity.getId().toString())
                            .setPhrase(entity.getPhrase())
                            .build())
                    .forEach(response -> responseObserver.onNext(response));
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public void remove(GreetingId request, StreamObserver<Empty> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            GreetingEntity entity = entityManager.getReference(GreetingEntity.class, id);
            entityManager.remove(entity);

        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void update(GreetingRequest request, StreamObserver<Empty> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            GreetingEntity entity = entityManager.getReference(GreetingEntity.class, id);
            entity.setPhrase(request.getPhrase());

            entityManager.persist(entity);
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        }
    }

}
