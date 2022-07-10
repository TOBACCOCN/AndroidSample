// package com.example.sample;
//
// import android.content.Context;
//
// import org.junit.Rule;
// import org.junit.Test;
// import org.junit.runner.RunWith;
//
// import androidx.test.ext.junit.rules.ActivityScenarioRule;
// import androidx.test.ext.junit.runners.AndroidJUnit4;
// import androidx.test.filters.LargeTest;
// import androidx.test.platform.app.InstrumentationRegistry;
//
// import static androidx.test.espresso.Espresso.onView;
// import static androidx.test.espresso.action.ViewActions.click;
// import static androidx.test.espresso.action.ViewActions.typeText;
// import static androidx.test.espresso.assertion.ViewAssertions.matches;
// import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
// import static androidx.test.espresso.matcher.ViewMatchers.withId;
// import static androidx.test.espresso.matcher.ViewMatchers.withText;
// import static org.junit.Assert.assertEquals;
//
// /**
//  * Instrumented test, which will execute on an Android device.
//  *
//  * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
//  */
// @RunWith(AndroidJUnit4.class)
// @LargeTest
// public class ExampleInstrumentedTest {
//
//     @Rule
//     public ActivityScenarioRule<MainActivity> activityRule =
//             new ActivityScenarioRule<>(MainActivity.class);
//
//     @Test
//     public void useAppContext() {
//         // Context of the app under test.
//         Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//         assertEquals("com.example.sample.free", appContext.getPackageName());
//     }
//
//     @Test
//     public void greeterSaysHello() {
//         onView(withId(R.id.tv)).perform(typeText("Steve"));
//         onView(withId(R.id.btn_bind)).perform(click());
//         onView(withText("Steve!")).check(matches(isDisplayed()));
//     }
// }