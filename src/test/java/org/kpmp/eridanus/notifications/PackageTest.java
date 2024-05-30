package org.kpmp.eridanus.notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class PackageTest {

	private Package pkg;

	@BeforeEach
	public void setUp() throws Exception {
		pkg = new Package();
	}

	@AfterEach
	public void tearDown() throws Exception {
		pkg = null;
	}

	@Test
	public void testSetPackageId() {
		pkg.setPackageId("packageId");
		assertEquals("packageId", pkg.getPackageId());
	}

	@Test
	public void testSetPackageType() {
		pkg.setPackageType("packageType");
		assertEquals("packageType", pkg.getPackageType());
	}

	@Test
	public void testSetCreatedAt() {
		Date createdAt = new Date();
		pkg.setCreatedAt(createdAt);
		assertEquals(createdAt, pkg.getCreatedAt());
	}

	@Test
	public void testSetSubjectId() {
		pkg.setSubjectId("subjectId");
		assertEquals("subjectId", pkg.getSubjectId());
	}

	@Test
	public void testSetSubmitter() {
		User submitter = new User();
		pkg.setSubmitter(submitter);
		assertEquals(submitter, pkg.getSubmitter());
	}

}
