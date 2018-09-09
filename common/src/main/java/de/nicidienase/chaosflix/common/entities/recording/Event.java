package de.nicidienase.chaosflix.common.entities.recording;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 11.03.17.
 */

public class Event extends SugarRecord implements Parcelable, Comparable<Event> {

	@SerializedName("conference_id")
	int conferenceId;
	String guid;
	String title;
	String subtitle;
	String slug;
	String link;
	String description;
	@SerializedName("original_language")
	String originalLanguage;
	List<String> persons;
	List<String> tags;
	String date;
	@SerializedName("release_date")
	String releaseDate;
	@SerializedName("updated_at")
	String updatedAt;
	int length;
	@SerializedName("thumb_url")
	String thumbUrl;
	@SerializedName("poster_url")
	String posterUrl;
	@SerializedName("frontend_link")
	String frontendLink;
	String url;
	@SerializedName("conference_url")
	String conferenceUrl;
	List<Recording> recordings;
	boolean promoted;

	public boolean isPromoted() {
		return promoted;
	}

	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	@SerializedName("view_count")
	int viewCount;


	protected Event(Parcel in) {
		conferenceId = in.readInt();
		guid = in.readString();
		title = in.readString();
		subtitle = in.readString();
		slug = in.readString();
		link = in.readString();
		description = in.readString();
		originalLanguage = in.readString();
		persons = in.createStringArrayList();
		tags = in.createStringArrayList();
		date = in.readString();
		releaseDate = in.readString();
		updatedAt = in.readString();
		length = in.readInt();
		thumbUrl = in.readString();
		posterUrl = in.readString();
		frontendLink = in.readString();
		url = in.readString();
		conferenceUrl = in.readString();
		recordings = in.createTypedArrayList(Recording.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(conferenceId);
		dest.writeString(guid);
		dest.writeString(title);
		dest.writeString(subtitle);
		dest.writeString(slug);
		dest.writeString(link);
		dest.writeString(description);
		dest.writeString(originalLanguage);
		dest.writeStringList(persons);
		dest.writeStringList(tags);
		dest.writeString(date);
		dest.writeString(releaseDate);
		dest.writeString(updatedAt);
		dest.writeInt(length);
		dest.writeString(thumbUrl);
		dest.writeString(posterUrl);
		dest.writeString(frontendLink);
		dest.writeString(url);
		dest.writeString(conferenceUrl);
		dest.writeTypedList(recordings);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Event> CREATOR = new Creator<Event>() {
		@Override
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}

		@Override
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};

	@Override
	public int compareTo(Event event) {
		return getSlug().compareTo(event.getSlug());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Event) {
			return getGuid().equals(((Event) obj).getGuid());
		} else {
			return super.equals(obj);
		}

	}

	public void update(Event e) {
		if (!this.updatedAt.equals(e.getUpdatedAt())) {
			// TODO actually update
			this.save();
		}
	}

	public Recording getOptimalStream() {
		List<Recording> result = new ArrayList<>();
		for(Recording r : getRecordings()){
			if(r.isHighQuality() && r.getMimeType().equals("video/mp4"))
				result.add(r);
		}
		if(result.size()== 0){
			for(Recording r : getRecordings()){
				if(r.getMimeType().equals("video/mp4"))
					result.add(r);
			}
		}
		if(result.size()== 0){
			for(Recording r : getRecordings()){
				if(r.getMimeType().startsWith("video/"))
					result.add(r);
			}
		}
		// sort by length of language-string in decending order, so first item has most languages
		Collections.sort(result,(o1, o2) -> o2.getLanguage().length() - o1.getLanguage().length());
		if(result.size() > 0){
			return result.get(0);
		} else {
			return getRecordings().get(0);
		}
	}

	public String getExtendedDescription(){
		StringBuilder sb = new StringBuilder();
		sb.append(getDescription())
				.append("\n")
				.append("\nreleased at: ").append(getReleaseDate())
				.append("\nTags: ").append(android.text.TextUtils.join(", ", getTags()));
		return sb.toString();
	}

	public String getSpeakerString(){
		return TextUtils.join(", ", getPersons());
	}

//	@BindingAdapter({"bind:imageUrl"})
//	public static void loadImage(ImageView imageView, String url){
//		Picasso.with(imageView.getContext())
//				.load(url)
//				.noFade()
//				.into(imageView);
//	}

	public String getApiID() {
		String[] strings = getUrl().split("/");
		return strings[strings.length - 1];
	}

	public String getConferenceId() {
		String[] split = conferenceUrl.split("/");
		return split[split.length - 1];
	}

	public void setConferenceId(int conferenceId) {
		this.conferenceId = conferenceId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public List<String> getPersons() {
		return persons;
	}

	public void setPersons(List<String> persons) {
		this.persons = persons;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getFrontendLink() {
		return frontendLink;
	}

	public void setFrontendLink(String frontendLink) {
		this.frontendLink = frontendLink;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getConferenceUrl() {
		return conferenceUrl;
	}

	public void setConferenceUrl(String conferenceUrl) {
		this.conferenceUrl = conferenceUrl;
	}

	public List<Recording> getRecordings() {
		return recordings;
	}

	public void setRecordings(List<Recording> recordings) {
		this.recordings = recordings;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}
