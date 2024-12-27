# Device Topology Exercise

This is implementation of device topology exercise. It is implemented as java (version 21) spring boot application
(build via gradle).

The web-app runs on port 8080, REST api is under `/api/v1/device` prefix. There is openapi available on runtime:

- access [spec](http://localhost:8080/v3/api-docs) to get Open API specification
- access [editor](http://localhost:8080/swagger-ui.html) to get UI app that allows invoking and examining the API

## Design

The assignment does not specify every detail, so there were few tweaks and decisions how to approach the problem.

The webapp is a thin wrapper around `cz.caitt.ubiquity.assignment.model.DeviceAPI` interface that models the whole
domain. The app has single controller that simply expose the interface to REST API, relying on JSON bindings. The
exceptions are mapped to proper status codes.

The implementation is in-memory only - restarting server erases all previous operations. Given the REST API that can be
called concurrently by many clients, the implementation is synchronized to avoid race-conditions.

If an argument is optional it may be passed as `null`; otherwise `null` triggers `NullPointerException`. Operation never
returns null. If an operation tries to find item by specific mac address, and it does not find anything it throws
`DeviceNotFoundException`. If an operation returns collection, it simply returns an empty collection.

`registerDevice` returns the newly created device to make the API easier to use (mainly for unit tests)

`registerDevice` handles error conditions to maintain topology invariant

- it is not possible to add the same mac address twice
- it is not possible to specify uplink address that does not exist already
- it is always possible to register device without uplink address (this means it is possible to create disconnected
  networks)

`retrieveAllDevices` sorts items first in order Gateway, Switch, Access Point in case of tie by its address. The second
part is not a requirement, but it makes the output deterministic.

`retrieveAllTopologies` returns list of trees given we allow for disconnected networks in `registerDevice`.

MAC address is represented by MacAddress class. This ensures only strings representing valid MAC address. The system
could easily work with any identifiers but based on my interpretation of the assignment, it was important to maintain
that only MAC addresses
are used as IDs. Also this allows to treat "00:00:00:00:00:ff" and "00:00:00:00:00:FF" as the same value.

## How to run

#### build

```shell
  ./gradlew build
```

#### tests

```shell
  ./gradlew test
```

#### app

```shell
  ./gradlew bootRun
```
