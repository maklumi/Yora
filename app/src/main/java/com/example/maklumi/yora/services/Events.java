package com.example.maklumi.yora.services;

public final class Events {
	private Events() {
	}

	public static final int OPERATION_CREATED = 0;
	public static final int OPERATION_DELETED = 1;

	public static final int ENTITY_CONTACT_REQUEST = 1;
	public static final int ENTITY_CONTACT = 2;
	public static final int ENTITY_MESSAGE = 3;

	public static class OnNotificationRecveivedEvent {
		public int OperationType;
		public int EntityType;
		public int EntityId;
		public int EntityOwnerId;
		public String EntityOwnerName;

		public OnNotificationRecveivedEvent(int operationType, int entityType, int entityId, int entityOwnerId, String entityOwnerName) {
			OperationType = operationType;
			EntityType = entityType;
			EntityId = entityId;
			EntityOwnerId = entityOwnerId;
			EntityOwnerName = entityOwnerName;
		}
	}
}
