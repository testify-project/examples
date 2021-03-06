# Change Log
All notable changes to this project will be documented in this file. This project
adheres to [Semantic Versioning](http://semver.org/). The change log file consists
of sections listing each version and the date they were released along with what
was added, changed, deprecated, removed, fix and security fixes.

- Added - Lists new features
- Changed - Lists changes in existing functionality
- Deprecated -  Lists once-stable features that will be removed in upcoming releases
- Removed - Lists deprecated features removed in this release
- Fixed - Lists any bug fixes
- Security - Lists security fixes to security vulnerabilities

## [Unreleased]

## [1.0.0] - 2018-01-04
### Added
- Added gRPC example

### Changed
- Updated Testify version to 1.0.2
- Updated Virtual Resource version to 1.0.2
- Updated Example code due to version updates
- Updated Spring version to 5.0.2.RELEASE
- Updated SpringBoot version to 1.5.7.RELEASE
- Updated HK2 version to 2.5.0-b42
- Updated Jersey 2 version to 2.26
- Updated Guice version to 4.1.0


## [0.9.3] - 2017-07-19
### Changed
- Updated Testify version to 0.9.6
- Updated Build Tools version to 0.9.7
- Updated Local Resource version to 0.9.4
- Updated Virtual Resource version to 0.9.2
- Updated Example code due to version updates

## [0.9.2] - 2017-06-07
### Changed
- Upgraded Testify API version to 0.9.4 and adopted Semantic Testing
  - Updated references to @RequiresResource annotation to @LocalResource
  - Updated references to @RequiresContainer annotation to @VirtualResource
  - Updated references to ResourceProvder contract to LocalResourceProvider
  - Updated references to ContainerProvider contract to VirtualResourceProvider
  - Updated references to ResourceInstance contract to LocalResourceInstance
  - Updated references to ContainerInstance contract to VirtualResourceInstance
  - Updated references to DefaultContainerInstance to DefaultVirtualResourceInstance
  - Updated references to DefaultResourceInstance to DefaultLocalResourceInstance
  - Updated references to ResourceInstanceBuilder to LocalResourceInstanceBuilder

## [0.9.1] - 2017-03-21
### Changed
- Updated Spring, SpringBoot, Jersey and Guice versions

## [0.9.0] - 2017-03-21
### Added
- Example JUnit4 Unit Test
- Example JUnit4 Spring Integration Test
- Example JUnit4 SpringBoot System Test
- Example JUnit4 Spring System Test
- Example JUnit4 HK2 Integration Test
- Example JUnit4 Jersey 2 System Test
- Example JUnit4 Guice Integration Test
- Example JUnit4 Resource Provider
