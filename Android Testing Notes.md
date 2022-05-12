# Tests

### Writing tests name

```
subjectUnderTest_actionOrInput_resultState
```

### Testing Pyramid

Three testing aspects in testing strategy:

**Scope** which part of the code is being tested

**Speed** How fast the test runs

**Fidelity** How real is the test scenario. Does it access the real features it's testing or does it fake the result?

These aspects are usually related. For example, speed and fidelity, the faster the test, generally, the less fidelity, and vice versa. 

#### Automated tests categories

**Unit tests** Highly focused tests. Usually run on a single class or even a single method. They have low fidelity and are fast enough to run every time you change the code.

**Integration tests** They test the integration between classes, making sure they work as expected when used together. These tests have a larger scope of code, but are still optimized to run fast, versus having full fidelity. One way to build integration tests is to test a single feature.

**End to end (E2E)** They test a combination of features working together, taking a large part of the app as scope. They have a high fidelity and show that your app works as a whole, therefore they are usually slow.

![ed5e6485d179c1b9.png](https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-test-doubles/img/ed5e6485d179c1b9.png) 

### Architecture and Testing

The ability to test the app is related with the app architecture. It's hard to create unit tests for a method which has all logic of the application. A good practice is to break the application logic in multiple methods an classes, making easier to test each method. Architecture is a good way to divide up and organize your code

### Launch and Test a Fragment

Fragments are visual and make up  the user interface. Because of this, when testing them, it helps to  render them on a screen, as they would when the app is running. Thus  when testing fragments, you usually write instrumented tests, which live in the `androidTest` source set.

**Annotations**

- **@MediumTest** Marks the test as a "medium run-time" integration test (versus  @SmallTest unit tests and  @LargeTest end-to-end tests). This helps you group and choose which size of test to run.

- **@RunWith(AndroidJUnit4::class)** - Used in any class using AndroidX Test.

**FragmentScenario** is a class from AndroidX Test that gives you control over thr fragment's lifecycle for testing

**ServiceLocator**

To write a fragment and view model integration test it's not possible to use constructor dependency injection, since fragments and activities are classes that you don't construct and don't have access to the constructor.

For this you can use ServiceLocator pattern that involves creating a singleton class called the "Service Locator", whose purpose is to provide dependencies, both for the regular and test code.

