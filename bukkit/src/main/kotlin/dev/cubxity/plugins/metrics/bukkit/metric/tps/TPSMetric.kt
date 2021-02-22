/*
 *     UnifiedMetrics is a fully-featured metrics collection plugin for Minecraft servers.
 *     Copyright (C) 2021  Cubxity
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.cubxity.plugins.metrics.bukkit.metric.tps

import dev.cubxity.plugins.metrics.api.UnifiedMetrics
import dev.cubxity.plugins.metrics.api.metric.Metric
import dev.cubxity.plugins.metrics.bukkit.bootstrap.UnifiedMetricsBukkitBootstrap
import dev.cubxity.plugins.metrics.bukkit.util.Environment
import dev.cubxity.plugins.metrics.common.measurement.TPSMeasurement

class TPSMetric(private val bootstrap: UnifiedMetricsBukkitBootstrap) : Metric<TPSMeasurement> {
    private val provider = if (Environment.majorMinecraftVersion >= 15 && Environment.isPaper) {
        PaperTPSProvider()
    } else {
        NMSTPSProvider()
    }

    override val isSync: Boolean
        get() = true

    override fun initialize() {
        bootstrap.logger.info("TPSMetric: Using ${provider.javaClass.name}")
    }

    override fun getMeasurements(api: UnifiedMetrics): List<TPSMeasurement> {
        return listOf(TPSMeasurement(provider.tps, provider.mspt))
    }
}