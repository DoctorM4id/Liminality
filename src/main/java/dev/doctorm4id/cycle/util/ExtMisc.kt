package dev.doctorm4id.cycle.util

import net.minecraft.world.level.Level

fun Level.everySeconds(secs: Int, offset: Long = 0L, func: () -> Unit) {
	if (((gameTime + (secs * 20) + offset) % 20) == 0L) {
		func()
	}

}
