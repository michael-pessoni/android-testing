# Tests

### Writing tests name

```
subjectUnderTest_actionOrInput_resultState
```

### Testing Pyramid

Three testing aspects in testing strategy:

**Scope** Witch part of the code is being tested

**Speed** How fast the test runs

**Fidelity** How real is the test scenario. Does it access the real features it's testing or does it fake the result?

These aspects are usually related. For example, speed and fidelity, the faster the test, generally, the less fidelity, and vice versa. 

#### Automated tests categories

**Unit tests** Highly focused tests. Usually run on a single class or even a single method. They have low fidelity and are fast enough to run every time you change the code.

**Integration tests** They test the integration between classes, making sure they work as expected when used together. These tests have a larger scope of code, but are still optimized to run fast, versus having full fidelity. One way to build integration tests is to test a single feature.

**End to end (E2E)** They test a combination of features working together, taking a large part of the app as scope. They have a high fidelity and show that your app works as a whole, therefore they are usually slow.

![ed5e6485d179c1b9.png](https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-test-doubles/img/ed5e6485d179c1b9.png) 

### Architecture and Testing

The ability to test the app is related with the app architecture. It's hard to create unit tests for a method witch has all logic of the application. A good practice is to break the application logic in multiple methods an classes, making easier to test each method. Architecture is a good way to divide up and organize your code

.