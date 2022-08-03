This is inspired by https://www.baeldung.com/jpa-entity-graph

In this project I implemented few approaches to problem with fetch eager/lazy:

1) PostEntityGraphRepositoryImpl - use entity manager and EntityGraph
2) PostRepository#getByIdEagerWithEntityGraph, PostRepository#getByIdLazyWithEntityGraph - use spring JPQL and
   EntityGraph
3) PostRepository#getByIdEager, PostRepository#getByIdLazy use only JPQL
   And all approaches are tested in PostRepositoryTest