package id.web.androidhyper.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static id.web.androidhyper.bakingapp.ConstantUtils.KEY_PASSMAINDATA;
import static id.web.androidhyper.bakingapp.ListReceiveTest.withRecyclerView;
import static org.hamcrest.Matchers.not;


/**
 * Created by wildhan on 9/17/2017 in BakingApp.
 * Keep Spirit!!
 */
@RunWith(AndroidJUnit4.class)
public class StepsReceiveTest {

    @Rule
    public ActivityTestRule<StepsActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(StepsActivity.class,true,false);


    @Test
    public void isListViewed(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        MainModel model = new MainModel();
        model.setName("TestUI");

        List<IngredientModel> ingredientModels = new ArrayList<>();
        ingredientModels.add(new IngredientModel(1.0,"KG","TESTUIINGREDIENS"));

        List<StepModel> stepModel = new ArrayList<>();
        stepModel.add(new StepModel(1
                ,"testUI"
                ,"testUIDesc"
                ,"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"
                ,"testUIThumb"));
        stepModel.add(new StepModel(2
                ,"testUI"
                ,"testUIDesc"
                ,""
                ,"testUIThumb"));

        model.setSteps(stepModel);
        model.setIngredients(ingredientModels);

        bundle.putParcelable(ConstantUtils.KEY_SELECTED_RECEIVE,model);

        intent.putExtra(KEY_PASSMAINDATA,bundle);

        mainActivityActivityTestRule.launchActivity(intent);

        //test on ingredients
        onView(withRecyclerView(R.id.rv_steps).atPositionOnView(0,R.id.container_ingredients))
                .check(matches(hasDescendant(withText("1.(1.0 KG) TESTUIINGREDIENS"))));

        //test text on recyclerview
        onView(withRecyclerView(R.id.rv_steps).atPosition(1))
                .check(matches(hasDescendant(withText("testUI"))));

        //check icon play is showed in ui when the receive has a video
        onView(withRecyclerView(R.id.rv_steps).atPositionOnView(1,R.id.iv_btplay))
                .check(matches(isDisplayed()));

        //check icon play is showed in ui when the receive has a video
        onView(withRecyclerView(R.id.rv_steps).atPositionOnView(2,R.id.iv_btplay))
                .check(matches(not(isDisplayed())));

        //check click button
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));


    }
    /*@Before
    public void initFragment(){
        mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();


    }*/



}
