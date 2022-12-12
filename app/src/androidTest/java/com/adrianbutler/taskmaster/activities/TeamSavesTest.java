package com.adrianbutler.taskmaster.activities;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.adrianbutler.taskmaster.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TeamSavesTest
{

	@Rule
	public ActivityScenarioRule<HomeActivity> mActivityScenarioRule =
			new ActivityScenarioRule<>(HomeActivity.class);

	@Test
	public void teamSavesTest()
	{
		ViewInteraction appCompatImageButton = onView(
				allOf(withId(R.id.HomeActivitySettingsButton), withContentDescription("Settings"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								3),
						isDisplayed()));
		appCompatImageButton.perform(click());

		ViewInteraction appCompatSpinner = onView(
				allOf(withId(R.id.SettingsActivitySelectTeamDropdown),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								4),
						isDisplayed()));
		appCompatSpinner.perform(click());

		DataInteraction materialTextView = onData(anything())
				.inAdapterView(childAtPosition(
						withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
						0))
				.atPosition(1);
		materialTextView.perform(click());

		ViewInteraction materialButton = onView(
				allOf(withId(R.id.SettingsSaveButton), withText("Save"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								3),
						isDisplayed()));
		materialButton.perform(click());

		pressBack();

		ViewInteraction textView = onView(
				allOf(withId(R.id.TaskFragmentTitle), withText("0. scrub the wall"),
						withParent(allOf(withId(R.id.frameLayout),
								withParent(withId(R.id.HomeActivityTaskRecyclerView)))),
						isDisplayed()));
		textView.check(matches(withText("0. scrub the wall")));
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
