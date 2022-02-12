package dt.mesaric.spacenews.data.remote.dao

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateConverter {

    @TypeConverter
    fun fromTimeStamp(value: Long?)
            = value?.let { LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)}

    @TypeConverter
    fun toTimeStamp(value: LocalDateTime?)
            = value?.atZone(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
}