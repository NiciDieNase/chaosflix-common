package de.nicidienase.chaosflix.common.entities

import android.arch.persistence.room.TypeConverter
class Converters {
    @TypeConverter
    fun longArrayToString(longArray: LongArray?): String
            = longArray?.joinToString(separator = "|") ?: ""

    @TypeConverter
    fun stringToLongArray(string: String?): LongArray
            = string?.split("|")?.filter { it.length > 0 }?.map { it.toLong() }?.toLongArray() ?: longArrayOf()

    @TypeConverter
    fun stringArrayToString(array: Array<String>): String
            = array.joinToString("|$|")

    @TypeConverter
    fun stringToStringArray(string: String): Array<String>
        = string.split("|$|").toTypedArray()

    @TypeConverter
    fun longMapToString(map: Map<Long,Long>): String{
        if(map.isNotEmpty()){
            return map.map { "${it.key}|${it.value}" }.reduce { acc, s -> "$acc|$s" }
        } else {
            return ""
        }
    }

    @TypeConverter
    fun stringToLongMap(string: String): Map<Long,Long> {
        val result = HashMap<Long,Long>()
        val list = string.split("|").filter { it.length > 0 }.map { it.toLong() }
        val iterator = list.iterator()
        while (iterator.hasNext()){
            result.put(iterator.next(),iterator.next())
        }
        return result
    }
}

