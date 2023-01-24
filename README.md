# NetworkAPI

NetworkAPI is an API designed to ease the development and communication of scalable Minecraft networks. It includes an Object-Relational Mapping (ORM) system, multiple popular pub/sub implementations, and other features to make it easy to create, manage, and communicate with Minecraft servers.

## Features

- Object-Relational Mapping (ORM) system: NetworkAPI includes an ORM system that allows for easy storage and retrieval of data in a relational database.

- Pub/Sub implementations: NetworkAPI supports multiple popular pub/sub implementations, including Redis and RabbitMQ, making it easy to set up and manage messaging between servers.

- Scalable: NetworkAPI is designed to be highly scalable, making it easy to create and manage large Minecraft networks with multiple servers.

- Easy to use: NetworkAPI has a simple and easy-to-use API that makes it easy to get started with creating and managing Minecraft servers.

- Open source: NetworkAPI is open source, meaning that it is free to use and modify.

## Getting Started

To get started with NetworkAPI, you can download the latest release from the [releases page](https://github.com/oskarscot/NetworkAPI/releases)

You can get started with NetworkAPI by getting the instance of the NetworkAPI class:
```java
  final NetworkAPI networkAPI = NetworkAPI.buildDefault();
```
Or using the builder:
```java
  final NetworkAPI networkAPI = NetworkAPI.builder()
    .setDatabaseProvider(PostgreSQLProvider.class)
    .setPubSubProvider(RedisPubSubProvider.class)
    .build();
```

Getting the database is as simple as calling:
```java

  final DatabaseService databaseService = networkAPI.getDatabaseService();
```

More documentation for NetworkAPI can be found on the [wiki](https://github.com/oskarscot/NetworkAPI/wiki)

