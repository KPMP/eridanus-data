package org.kpmp.eridanus.notifications;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pacakageNotificationEvents")
public class PackageNotificationEvent {

	@Id
	private String id;
	private String packageId;
	private String packageType;
	private Date datePackageSubmitted;
	private String submitter;
	private Date dateEventSubmitted;
	private String specimenId;
	private String origin;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public Date getDatePackageSubmitted() {
		return datePackageSubmitted;
	}

	public void setDatePackageSubmitted(Date dateSubmitted) {
		this.datePackageSubmitted = dateSubmitted;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateEventSubmitted() {
		return dateEventSubmitted;
	}

	public void setDateEventSubmitted(Date dateEventSubmitted) {
		this.dateEventSubmitted = dateEventSubmitted;
	}

	public String getSpecimenId() {
		return specimenId;
	}

	public void setSpecimenId(String specimenId) {
		this.specimenId = specimenId;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}
