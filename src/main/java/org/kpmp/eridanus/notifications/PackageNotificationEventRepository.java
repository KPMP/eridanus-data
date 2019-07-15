package org.kpmp.eridanus.notifications;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageNotificationEventRepository extends MongoRepository<PackageNotificationEvent, String> {

	@SuppressWarnings("unchecked")
	public PackageNotificationEvent save(PackageNotificationEvent event);

}
