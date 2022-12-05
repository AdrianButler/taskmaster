package com.adrianbutler.taskmaster;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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

import com.adrianbutler.taskmaster.activities.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestUsernameSaves
{

	@Rule
	public ActivityScenarioRule<HomeActivity> mActivityScenarioRule =
			new ActivityScenarioRule<>(HomeActivity.class);

	@Test
	public void testUsernameSaves()
	{
		ViewInteraction appCompatImageButton = onView(
				allOf(withId(R.id.HomeActivitySettingsButton), withContentDescription("Settings Button"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								8),
						isDisplayed()));
		appCompatImageButton.perform(click());

		ViewInteraction appCompatEditText = onView(
				allOf(withId(R.id.SettingsUsernameEditText),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								2),
						isDisplayed()));
		appCompatEditText.perform(replaceText("Adrian"), closeSoftKeyboard());

		ViewInteraction appCompatEditText2 = onView(
				allOf(withId(R.id.SettingsUsernameEditText), withText("Adrian"),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								2),
						isDisplayed()));
		appCompatEditText2.perform(pressImeActionButton());

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
				allOf(withId(R.id.HomeNameTextView), withText("Adrian's  tasks:"),
						withParent(withParent(withId(android.R.id.content))),
						isDisplayed()));
		textView.check(matches(withText("Adrian's  tasks:")));
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
