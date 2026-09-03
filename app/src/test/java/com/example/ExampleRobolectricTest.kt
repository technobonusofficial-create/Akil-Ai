package com.example

import android.Manifest
import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Akil", appName)
  }

  @Test
  fun `test android bridge openApp`() {
    val activityController = Robolectric.buildActivity(MainActivity::class.java).setup()
    val activity = activityController.get()
    val bridge = AndroidBridge(activity)
    val result = bridge.openApp("youtube")
    assert(result.contains("status"))
  }

  @Test
  fun `test android bridge callContact matching`() {
    val application = ApplicationProvider.getApplicationContext<Application>()
    shadowOf(application).grantPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE)

    val activityController = Robolectric.buildActivity(MainActivity::class.java).setup()
    val activity = activityController.get()
    val bridge = AndroidBridge(activity)
    val mummyResult = bridge.callContact("Mummy")
    assert(mummyResult.contains("status"))
  }
}

