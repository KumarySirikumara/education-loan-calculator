package com.example.educationloancalculator;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultActivityTest {

    @Rule
    public ActivityTestRule<ResultActivity> resultActivityTestRule = new ActivityTestRule<ResultActivity>(ResultActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, ResultActivity.class);
            result.putExtra("Name", "Earth");
            result.putExtra("lAmount", "10000");
            result.putExtra("iRate", "5");
            result.putExtra("lTerm", "1");
            result.putExtra("rPeriod", "YEARS");
            result.putExtra("iRateTerm", "YEAR");
            result.putExtra("activityFrom", "Calculator");

            return result;
        }
    };
    private ResultActivity resultActivity = null;
    @Before
    public void setUp() throws Exception {
        //get activity context
        resultActivity = resultActivityTestRule.getActivity();

    }

    @Test
    public void testLaunch(){
        //check activity whether started by checking TextView on app bar
        //which is id = appBarName
        //get reference and attach to a view object
        View view = resultActivity.findViewById(R.id.appBarName);
        //if it is created, assert should not be null
        assertNotNull(view);
    }

    //Testing the payment
    @Test
    public void testTotalPayment(){
        //payment should equal to 10500 for given details
        //Get TextView Reference
        TextView view = resultActivity.findViewById(R.id.totalPayment);
        //Assert true
        assertEquals("10500.00", view.getText().toString());
    }

    @After
    public void tearDown() throws Exception {
        //set activity context to null
        resultActivity = null;
    }
}