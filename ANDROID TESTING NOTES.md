# Tests

### Summary

- [Writing tests name](#Writing tests name)
- [Testing Pyramid](#Testing Pyramid)
- [Automated tests categories](#Automated tests categories)
- [Architecture and Testing](#Architecture and Testing)
- [Test Doubles](#Test Doubles)
  - [Fakes and Dependency Injection](#Fakes and Dependency Injection)
- [Testing suspend functions](#Testing suspend functions)
- [Testing UI with Espresso](#Testing UI with Espresso)
  - [Launch and Test a Fragment](#Launch and Test a Fragment)
  - [Espresso](#Espresso)
- [Testing Navigation](#Testing Navigation)
- [Testing Coroutines](#Testing Coroutines)
  - [runBlockingTest](#runBlockingTest)
  - [TestCoroutineDispatcher](#TestCoroutineDispatcher)
- [Testing Error Handling](#Testing Error Handling)



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

### Automated tests categories

**Unit tests** Highly focused tests. Usually run on a single class or even a single method. They have low fidelity and are fast enough to run every time you change the code.

**Integration tests** They test the integration between classes, making sure they work as expected when used together. These tests have a larger scope of code, but are still optimized to run fast, versus having full fidelity. One way to build integration tests is to test a single feature.

**End to end (E2E)** They test a combination of features working together, taking a large part of the app as scope. They have a high fidelity and show that your app works as a whole, therefore they are usually slow.

![ed5e6485d179c1b9.png](https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-test-doubles/img/ed5e6485d179c1b9.png) 

### Architecture and Testing

The ability to test the app is related with the app architecture. It's hard to create unit tests for a method which has all logic of the application. A good practice is to break the application logic in multiple methods an classes, making easier to test each method. Architecture is a good way to divide up and organize your code

### Test Doubles

The unit test should focus on a small part of the code and its behavior. This can be tricky because the class under test can depend on other classes, out of your test's scope. 

The solution for this cases is to use test doubles. A test double is a version of a class crafted specifically for testing. It is meant to replace the real version of a class in tests.

|   Types   | of Test Doubles                                              |
| :-------: | ------------------------------------------------------------ |
| **Fake**  | A test double that has a "working"  implementation of the class, but it's implemented in a way that makes it good for tests but unsuitable for production. |
| **Mock**  | A test double that tracks which of  its methods were called. It then passes or fails a test depending on  whether it's methods were called correctly. |
| **Stub**  | A test double that includes no logic and only returns what you program it to return. A `StubTaskRepository` could be programmed to return certain combinations of tasks from `getTasks` for example. |
| **Dummy** | A test double that is passed around but not used, such as if you just need to provide it as a parameter. If you had a `NoOpTaskRepository`, it would just implement the `TaskRepository` with **no** code in any of the methods. |
|  **Spy**  | A test double which also keeps tracks of some additional information; for example, if you made a `SpyTaskRepository`, it might keep track of the number of times the `addTask` method was called. |

[`Testing on the Toilet: Know your test doubles`](https://testing.googleblog.com/2013/07/testing-on-toilet-know-your-test-doubles.html) 

#### Fakes and Dependency Injection

When you build fakes to replace dependencies you need to guarantee the fake is used only in the tests and the real class is used on production code. For this you need to provide these dependencies using a technique called dependency injection. 

[`Dependency Injection in Android`](https://developer.android.com/training/dependency-injection)

### Testing suspend functions

Suspend functions will need to launch a coroutine to call it, and a coroutine scope for that. You can use `kotlinx-coroutines-test` library that is specifically meant for testing coroutines. 

To run the tests with suspend functions you can use  function `runBlockingTest`. This function takes in a block of code and then runs this block of code in a  special coroutine context which runs synchronously and immediately,  meaning actions will occur in a deterministic order. This essentially  makes your coroutines run like non-coroutines, so it is meant for  testing code. You'll have to add `@ExperimentalCoroutinesApi` above your test class, once you're using an experimental coroutine api (`runBlockingTest`).

### Testing UI with Espresso

#### Launch and Test a Fragment

Fragments are visual and make up  the user interface. Because of this, when testing them, it helps to  render them on a screen, as they would when the app is running. Thus  when testing fragments, you usually write instrumented tests, which live in the androidTest source set.

**Annotations**

- `@MediumTest` Marks the test as a "medium run-time" integration test (versus  @SmallTest unit tests and  @LargeTest end-to-end tests). This helps you group and choose which size of test to run.
- `@RunWith(AndroidJUnit4::class)` - Used in any class using AndroidX Test.

**AndroidX Test libraries** include classes and methods that provide you with versions of  components like Applications and Activities that are meant for tests.

**FragmentScenario** is a class from AndroidX Test that gives you control over the fragment's lifecycle for testing

**ServiceLocator**

To write a fragment and view model integration test it's not possible to use constructor dependency injection, since fragments and activities are classes that you don't construct and don't have access to the constructor.

For this you can use ServiceLocator pattern that involves creating a singleton class called the "Service Locator", whose purpose is to provide dependencies, both for the regular and test code.

#### Espresso

Espresso is a library that allows you to test state expectations, interactions and assertions clearly without the distraction of boilerplate content, custom infrastructure, or messy implementation details getting in the way.

Espresso helps you:

- Interact with views, like clicking buttons, sliding a bar, or scrolling down a screen.
- Assert that certain views are on screen or are in a certain state  (such as containing particular text, or that a checkbox is checked,  etc.)

**Statement parts**

```kotlin
onView(withId(R.id.task_detail_complete_checkbox)).perform(click()).check(matches(isChecked()))
```

- `Static Espresso method` starts an Espresso statement. For example "onView"
- `ViewMatcher` a matcher that matches a view. E.g. "withId"
- `ViewAction` is something that can be done to the view. E.g. "perform"
- `ViewAssertion` check or assert something about the view. E.g. "check"

### Testing Navigation

Navigation is a complicated action, that don't result in a clear output or state change, only initializing a new Fragment. To test it we can assert that the right method (navigate) was called with the correct action parameter. For this we can use a mock test double.

**Mockito** is a framework for making test doubles. While the word mock is used in the API and name, it is not for just making mocks. It can also make stubs and spies.

To mock in Mockito, pass in the class you want to mock

```Kotlin
 val navController = mock(NavController::class.java)
```

Mockito's  `verify` method is what makes this a mock, you're able to confirm the mocked class called a specific method with a parameter

### Testing Coroutines

Testing asynchronous code can be difficult for some reasons. First is that asynchronous code tends to be non-deterministic, which means that if the test runs two operations in parallel sometimes one task will finish first and sometimes the other one can finishes first. This can cause flaky tests (with inconsistent results). A second reason is that tests run on a testing thread. As your test runs code on different  threads, or makes new coroutines, this work is started asynchronously,  separate from the test thread.  Meanwhile the test coroutine will keep executing instructions in  parallel. The test might finish before either of the fired-off tasks  finish. 

When testing asynchronous code, you need to make your code deterministic and provide synchronization mechanisms. You can use some tool as `runBlockingTest` and `runBlocking`, `TestCoroutineDispatcher`or pausing coroutine execution to test the state of the code at an exact place in time.

#### runBlockingTest 

Look back in [Testing suspend functions](#Testing suspend functions). 
Writing test doubles, use `runBlocking.`

#### TestCoroutineDispatcher

All coroutines require a `CoroutineScope`that control the lifetimes of coroutines. On a view model you can use `viewModelScope`(Read more about [viewModelScope](https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471)) that returns a CoroutineScope associated with each view model. It uses `Dispatchers.Main`that uses Android's  [`Looper.getMainLooper()`](https://developer.android.com/reference/kotlin/android/os/Looper.html#getMainLooper()). The main looper is the execution loop for a real application. The main  looper is not available (by default) in local tests, because you're not  running the full application.

To address this, use the method  `setMain()` (from `kotlinx.coroutines.test`) to modify `Dispatchers.Main` to use  [`TestCoroutineDispatcher`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-dispatcher/index.html). `TestCoroutineDispatcher` is a dispatcher specifically meant for testing.

```kotlin
@ExperimentalCoroutinesApi
val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

@ExperimentalCoroutinesApi
@Before
fun setupDispatcher() {
    Dispatchers.setMain(testDispatcher)
}

@ExperimentalCoroutinesApi
@After
fun tearDownDispatcher() {
    Dispatchers.resetMain()
    testDispatcher.cleanupTestCoroutines()
}
```

The TestCoroutineDispatcher executes tasks immediately and completely, which means that before the assert statements are executed, the method you called has completely finished. In some cases you want to make an assertion in the middle of the execution, then you can use TestCoroutineDispatcher's `pauseDispatcher` and `resumeDispatcher`. When the dispatcher is paused any new coroutines are added to a queue  rather than being executed immediately. This means that code execution  inside the method will be paused *just before* the coroutine is launched, and then executed when resumeDispatcher is called.

### Testing Error Handling

In tests it's important also testing what your app does when it encounters errors, for example when the network is down and it can't load data. 

First, you need to artificially cause the error situation. One way to do this is to update your test doubles so that you can "set" them to an  error state, using a flag. If the flag is `false`, the test double functions as normal. But if the flag is set to `true`, then the test double returns a realistic error; for example, it might return a failure to load data error. Then you can write tests for these error states.

