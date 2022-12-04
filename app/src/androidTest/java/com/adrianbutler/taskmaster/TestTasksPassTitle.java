package com.adrianbutler.taskmaster;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestTasksPassTitle
{

	@Rule
	public ActivityScenarioRule<HomeActivity> mActivityScenarioRule =
			new ActivityScenarioRule<>(HomeActivity.class);

	@Test
	public void testTasksPassTitle()
	{
		ViewInteraction materialButton = onView(
				allOf(withId(R.id.HomeVacuumTaskButton), withText("Vacuum"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								5),
						isDisplayed()));
		materialButton.perform(click());

		ViewInteraction textView = onView(
				allOf(withId(R.id.TaskDetailTitleText), withText("Vacuum"),
						withParent(withParent(withId(android.R.id.content))),
						isDisplayed()));
		textView.check(matches(withText("Vacuum")));

		pressBack();

		ViewInteraction materialButton2 = onView(
				allOf(withId(R.id.HomeMowLawnTaskButton), withText("Mow Lawn"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								6),
						isDisplayed()));
		materialButton2.perform(click());

		ViewInteraction textView2 = onView(
				allOf(withId(R.id.TaskDetailTitleText), withText("Mow Lawn"),
						withParent(withParent(withId(android.R.id.content))),
						isDisplayed()));
		textView2.check(matches(withText("Mow Lawn")));

		pressBack();

		ViewInteraction materialButton3 = onView(
				allOf(withId(R.id.HomeCleanRoomTaskButton), withText("Clean Room"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								7),
						isDisplayed()));
		materialButton3.perform(click());

		ViewInteraction textView3 = onView(
				allOf(withId(R.id.TaskDetailTitleText), withText("Clean Room"),
						withParent(withParent(withId(android.R.id.content))),
						isDisplayed()));
		textView3.check(matches(withText("Clean Room")));
	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position)
	{

		return new TypeSafeMatcher<View>()
		{
			@Override
			public void describeTo(Description description)
			{
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view)
			{
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}
