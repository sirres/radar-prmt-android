/*
 * Copyright 2017 The Hyve
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.radarcns.detail

import android.Manifest.permission.RECEIVE_BOOT_COMPLETED
import android.os.Build
import org.radarbase.android.RadarService
import org.radarbase.android.config.SingleRadarConfiguration
import org.radarbase.android.source.SourceProvider
import org.radarbase.monitor.application.ApplicationStatusProvider
import org.radarbase.passive.audio.OpenSmileAudioProvider
import org.radarbase.passive.bittium.FarosProvider
import org.radarbase.passive.empatica.E4Provider
import org.radarbase.passive.phone.PhoneBluetoothProvider
import org.radarbase.passive.phone.PhoneContactListProvider
import org.radarbase.passive.phone.PhoneLocationProvider
import org.radarbase.passive.phone.PhoneSensorProvider
import org.radarbase.passive.phone.usage.PhoneUsageProvider
import org.radarbase.passive.ppg.PhonePpgProvider
import org.radarbase.passive.weather.WeatherApiProvider
import java.util.*

class RadarServiceImpl : RadarService() {
    override val plugins: List<SourceProvider<*>> = listOf(
            ApplicationStatusProvider(this),
            OpenSmileAudioProvider(this),
            E4Provider(this),
            FarosProvider(this),
            PhoneBluetoothProvider(this),
            PhoneContactListProvider(this),
            PhoneLocationProvider(this),
            PhoneSensorProvider(this),
//            PhoneLogProvider(this),
            PhoneUsageProvider(this),
            PhonePpgProvider(this),
            WeatherApiProvider(this)
    )

    override val servicePermissions: List<String>
        get() {
            return ArrayList(super.servicePermissions).also {
                it += RECEIVE_BOOT_COMPLETED
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it += REQUEST_IGNORE_BATTERY_OPTIMIZATIONS_COMPAT
                }
            }
        }

    override fun doConfigure(config: SingleRadarConfiguration) {
        super.doConfigure(config)
        configureRunAtBoot(config, MainActivityBootStarter::class.java)
    }
}
