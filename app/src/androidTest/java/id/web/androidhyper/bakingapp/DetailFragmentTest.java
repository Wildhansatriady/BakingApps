package id.web.androidhyper.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import id.web.androidhyper.bakingapp.model.IngredientModel;
import id.web.androidhyper.bakingapp.model.MainModel;
import id.web.androidhyper.bakingapp.model.StepModel;
import id.web.androidhyper.bakingapp.ui.stepssegment.StepsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_PASSMAINDATA;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_POSITION_LIST;

/**
 * Created by wildhan on 9/17/2017 in BakingApp.
 * Keep Spirit!!
 */

@RunWith(AndroidJUnit4.class)
public class DetailFragmentTest {
    @Rule
    public ActivityTestRule<StepsActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(StepsActivity.class,true,false);

    @Before
    public void init(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        MainModel model = new MainModel();
        model.setName("TestUI");

        List<IngredientModel> ingredientModels = new ArrayList<>();
        ingredientModels.add(new IngredientModel(1.0,"KG","TESTUIINGREDIENS"));

        List<StepModel> stepModel = new ArrayList<>();
        stepModel.add(new StepModel(0
                ,"testUI"
                ,"testUIDesc"
                ,"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"
                ,"testUIThumb"));
        stepModel.add(new StepModel(1
                ,"testUI2"
                ,"testUIDesc2"
                ,""
                ,"testUIThumb"));

        model.setSteps(stepModel);
        model.setIngredients(ingredientModels);

        bundle.putParcelable(ConstantUtils.KEY_SELECTED_RECEIVE,model);
        bundle.putInt(KEY_POSITION_LIST,0);

        intent.putExtra(KEY_PASSMAINDATA, bundle);

        mainActivityActivityTestRule.launchActivity(intent);
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

    }

    @Test
    public void testContentDetail(){
        //startDetailStepsFragment();
        onView(withId(R.id.short_desc)).check(matches((isDisplayed())));
        onView(withId(R.id.player_view)).check(matches((isDisplayed())));
        onView(withId(R.id.short_desc)).check(matches((withText("testUI"))));

        onView(withId(R.id.tv_contentdesc)).check(matches((isDisplayed())));
        onView(withId(R.id.tv_contentdesc)).check(matches((withText("testUIDesc"))));

        onView(withId(R.id.bt_next)).check(matches((isDisplayed())));
        onView(withId(R.id.bt_next)).perform(click());

        onView(withId(R.id.short_desc)).check(matches((isDisplayed())));
        onView(withId(R.id.short_desc)).check(matches((withText("testUI2"))));

        onView(withId(R.id.tv_contentdesc)).check(matches((isDisplayed())));
        onView(withId(R.id.tv_contentdesc)).check(matches((withText("testUIDesc2"))));

        onView(withId(R.id.bt_prev)).check(matches((isDisplayed())));
        onView(withId(R.id.bt_prev)).perform(click());
        onView(withId(R.id.short_desc)).check(matches((isDisplayed())));
        onView(withId(R.id.short_desc)).check(matches((withText("testUI"))));

        onView(withId(R.id.tv_contentdesc)).check(matches((isDisplayed())));
        onView(withId(R.id.tv_contentdesc)).check(matches((withText("testUIDesc"))));



    }

}
