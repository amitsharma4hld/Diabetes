package com.s.diabetesfeeding

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.network.ApiInterface
import com.s.diabetesfeeding.data.network.NetworkConnectionInterceptor
import com.s.diabetesfeeding.data.preferences.PreferenceProvider
import com.s.diabetesfeeding.data.repositories.MonitorbloodGlucoseRepository
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.ui.auth.AuthViewModelFactory
import com.s.diabetesfeeding.ui.auth.ForgotPassModelFactory
import com.s.diabetesfeeding.ui.home.HomeViewModelFactory
import com.s.diabetesfeeding.ui.home.MonitorBloodGlucoseViewModelFactory
import com.s.diabetesfeeding.ui.home.fragment.diabetes.ProgressBloodGlucoseModelFactory
import com.s.diabetesfeeding.ui.home.fragment.diabetes.ProgressBloodGlucoseRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val prefs: PreferenceProvider by lazy {
    MVVMApplication.prefs!!
}

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))
        AndroidThreeTen.init(this@MVVMApplication);
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { MonitorbloodGlucoseRepository(instance(), instance(),instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ForgotPassModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider{ MonitorBloodGlucoseViewModelFactory(instance())}
        bind() from provider{ ProgressBloodGlucoseModelFactory(instance())}
        bind() from provider{ ProgressBloodGlucoseRepository(instance())}

    }
    companion object {
        var prefs: PreferenceProvider? = null
    }
    override fun onCreate() {
        prefs = PreferenceProvider(applicationContext)
        super.onCreate()
    }

}