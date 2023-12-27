package com.material.components.mine

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.material.components.R
import com.material.components.activity.about.AboutCompanyImage
import com.material.components.activity.settings.SettingSectioned
import com.material.components.mine.fragment.DataVisualizationFragment
import com.material.components.mine.fragment.HomeFragment
import com.material.components.mine.login.GoogleSignInManager
import com.material.components.mine.tab.HiFragmentTabView
import com.material.components.mine.tab.HiTabViewAdapter
import com.material.components.mine.ui.tab.bottom.HiTabBottomInfo
import com.material.components.mine.ui.tab.bottom.HiTabBottomLayout
import com.material.components.utils.Tools
import com.mikhaellopez.circularimageview.CircularImageView
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.Calendar

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initView()
        initToolbar()
        initStatusBar()


        // Inflate the layout for this fragment
        initNavigationMenu()
        // Inflate the layout for this fragment
        requestPermission()
    }


    private fun requestPermission() {
        if (XXPermissions.isGranted(this, Permission.ACTIVITY_RECOGNITION)) {
            return
        }
        XXPermissions.with(this)
            .permission(Permission.ACTIVITY_RECOGNITION)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: List<String>, allGranted: Boolean) {
                    Toast.makeText(
                        this@MainActivity,
                        "Successfully obtained recording permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onDenied(permissions: List<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        Toast.makeText(
                            this@MainActivity,
                            "Permanently denied permission, please grant recording permission manually.",
                            Toast.LENGTH_SHORT
                        ).show()
                        XXPermissions.startPermissionActivity(
                            this@MainActivity,
                            permissions
                        )
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to obtain recording permission",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null

    private fun initToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setTitle("Health Data")
    }

    private fun initStatusBar() {
        Tools.setSystemBarColor(this, R.color.white)
        Tools.setSystemBarLight(this)
    }


    private fun initNavigationMenu() {
        val nav_view = findViewById<View>(R.id.nav_view) as NavigationView
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val headerView = nav_view.getHeaderView(0)
        val nameView = headerView.findViewById<TextView>(R.id.google_account_name)
        val emailView = headerView.findViewById<TextView>(R.id.email)
        val imageView = headerView.findViewById<CircularImageView>(R.id.avatar)
        val data = GoogleSignInManager.getInstance().googleAccountData
        if (data != null) {
            nameView.text = data.displayName
            emailView.text = data.accountId
            Glide.with(this).load(data.profilePictureUri).into(imageView)
        }
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.nav_report_time) {
                dialogTimePickerLight()
            } else if (item.itemId == R.id.nav_report_frequency) {
                showSingleChoiceDialog()
            } else if (item.itemId == R.id.nav_contact_us) {
                showDialogContactUS()
            } else if (item.itemId == R.id.nav_privacy) {
                showPrivacyAndPolicy()
            } else if (item.itemId == R.id.nav_privacy_settng) {
                showPrivacySettingPage()
            } else if (item.itemId == R.id.nav_about_us) {
                showAboutUsPage()
            } else if (item.itemId == R.id.nav_language) {
            }
            true
        }
    }

    private fun showPrivacySettingPage() {
        this.startActivity(Intent(this, SettingSectioned::class.java))
    }

    private fun showAboutUsPage() {
        this.startActivity(Intent(this, AboutCompanyImage::class.java))
    }

    private fun showSingleChoiceDialog() {
        single_choice_selected = Frequency[0]
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Your preferred report frequency")
        builder.setSingleChoiceItems(
            Frequency, 0
        ) { dialogInterface, i -> single_choice_selected = Frequency[i] }
        builder.setPositiveButton(
            R.string.OK
        ) { dialogInterface, i ->
            Snackbar.make(
                root!!,
                "selected : $single_choice_selected",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton(R.string.CANCEL, null)
        builder.show()
    }


    private var single_choice_selected: String? = null

    private val Frequency = arrayOf(
        "daily", "once every two days", "once every three days"
    )

    private fun showDialogContactUS() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_contact_us)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.findViewById<View>(R.id.dialog_contact_us_close)
            .setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun showPrivacyAndPolicy() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_gdpr_basic)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        (dialog.findViewById<View>(R.id.tv_content) as TextView).movementMethod =
            LinkMovementMethod.getInstance()
        (dialog.findViewById<View>(R.id.bt_accept) as Button).setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Button Accept Clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        (dialog.findViewById<View>(R.id.bt_decline) as Button).setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Button Decline Clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        dialog.show()
        dialog.window!!.attributes = lp
    }


    private fun dialogTimePickerLight() {
        val cur_calender = Calendar.getInstance()
        val datePicker = TimePickerDialog.newInstance(
            { view, hourOfDay, minute, second ->
                Snackbar.make(
                    root!!,
                    "selected : $hourOfDay:$minute",
                    Snackbar.LENGTH_SHORT
                ).show()
            },
            cur_calender[Calendar.HOUR_OF_DAY], cur_calender[Calendar.MINUTE], true
        )
        //set dark light
        datePicker.isThemeDark = false
        datePicker.accentColor = resources.getColor(R.color.colorPrimary)
        datePicker.show(fragmentManager, "Timepickerdialog")
    }

    private var root: DrawerLayout? = null


    private fun initView() {
        root = findViewById<DrawerLayout>(R.id.drawer_layout)

        val fragmentTabView = findViewById<HiFragmentTabView>(R.id.fragment_tab_view)
        val hiTabBottomLayout = findViewById<HiTabBottomLayout>(R.id.tab_bottom_layout)
        val infoList : MutableList<HiTabBottomInfo<*>> = ArrayList()
        val hiTabBottomInfo = HiTabBottomInfo(
            "Home", "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null, "#ff656667", "#ffd44949"
        )
        hiTabBottomInfo.setFragment(DataVisualizationFragment::class.java)
        infoList.add(hiTabBottomInfo)

        val hiTabBottomInfo2 = HiTabBottomInfo(
            "Message", "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null, "#ff656667", "#ffd44949"
        )
        hiTabBottomInfo2.setFragment(HomeFragment::class.java)
        infoList.add(hiTabBottomInfo2)
        hiTabBottomLayout.inflateInfo(infoList)
        hiTabBottomLayout.setTabAlpha(0.85f)

        val adapter  = HiTabViewAdapter(getSupportFragmentManager(), infoList)
        fragmentTabView.adapter = adapter
        fragmentTabView.currentItem = 0

        hiTabBottomLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            fragmentTabView.currentItem = index
        }
        hiTabBottomLayout.defaultSelected(infoList[0])

    }


}
