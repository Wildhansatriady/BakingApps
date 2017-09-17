package id.web.androidhyper.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import id.web.androidhyper.bakingapp.helper.RecyclerViewMatcher;
import id.web.androidhyper.bakingapp.ui.receivelist.ReceiveActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by wildhan on 8/27/2017 in BakingApp.
 * Keep Spirit!!
 */

@RunWith(AndroidJUnit4.class)
public class ListReceiveTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<ReceiveActivity> mainActivityActivityTestRule =
            new ActivityTestRule<ReceiveActivity>(ReceiveActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }
    @Before
    public void init(){
        mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
    @Test
    public void isListViewed(){
        onView(withRecyclerView(R.id.rvReceive).atPosition(1))
                .check(matches(hasDescendant(withText("Brownies"))));
        onView(withId(R.id.rvReceive)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

    }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
