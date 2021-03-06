package de.nicidienase.chaosflix.common.entities.streaming

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlin.collections.ArrayList

@JsonIgnoreProperties(ignoreUnknown = true)
class LiveConference(
    var conference: String,
    var slug: String,
    var author: String,
    var description: String,
    var keywords: String,
    var startsAt: String,
    var endsAt: String,
    var groups: MutableList<Group>){

    constructor(conference: String, description: String) : this(conference,description,"","","","","",ArrayList())

    companion object {

        val dummyObject: LiveConference
            get() {
                val dummyCon = LiveConference("DummyCon", "Conference McConferenceface")
                dummyCon.groups = ArrayList()
                dummyCon.groups.add(Group.dummyObject)
                dummyCon.slug = "duco"
                dummyCon.author = ""
                dummyCon.description = "A placeholder conference"
                dummyCon.keywords = ""
                return dummyCon
            }
    }
}
