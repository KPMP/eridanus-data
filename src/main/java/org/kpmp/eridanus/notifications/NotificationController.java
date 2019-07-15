package org.kpmp.eridanus.notifications;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private PackageNotificationEventService packageEventService;

	@Autowired
	public NotificationController(PackageNotificationEventService packageEventService) {
		this.packageEventService = packageEventService;
	}

	@RequestMapping(value = "/v1/notifications/package", method = RequestMethod.POST)
	public @ResponseBody Boolean notifyNewPackage(@RequestBody PackageNotificationEvent event,
			HttpServletRequest request) {
		
		log.info("URI: {} | MSG: {}", request.getRequestURI(),
				"Adding notification for PKGID: " + event.getPackageId());

		event = packageEventService.saveNotifyEvent(event);
		boolean emailSent = packageEventService.sendNotifyEmail(event);

		return emailSent;
	}

}
