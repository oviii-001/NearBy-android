package com.ovi.nearby.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.ovi.nearby.core.common.Resource
import com.ovi.nearby.core.constants.AppConstants
import com.ovi.nearby.domain.model.Group
import com.ovi.nearby.domain.model.GroupMember
import com.ovi.nearby.domain.model.MemberRole
import com.ovi.nearby.domain.repository.GroupRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GroupRepository {

    override suspend fun createGroup(name: String, description: String): Resource<Group> {
        return try {
            val groupId = UUID.randomUUID().toString()
            val inviteCode = UUID.randomUUID().toString().take(8).uppercase()
            val group = Group(
                id = groupId,
                name = name,
                description = description,
                createdBy = "", 
                createdAt = System.currentTimeMillis(),
                memberCount = 1,
                inviteCode = inviteCode
            )
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .set(group)
                .await()
            Resource.Success(group)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create group")
        }
    }

    override suspend fun getGroup(groupId: String): Resource<Group> {
        return try {
            val doc = firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .get()
                .await()
            val group = doc.toObject(Group::class.java)
            if (group != null) Resource.Success(group)
            else Resource.Error("Group not found")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch group")
        }
    }

    override suspend fun getUserGroups(): Resource<List<Group>> {
        return try {
            val groups = mutableListOf<Group>()
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .get()
                .await()
                .toObjects(Group::class.java)
                .let { groups.addAll(it) }
            Resource.Success(groups)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch groups")
        }
    }

    override suspend fun joinGroupWithCode(inviteCode: String): Resource<Group> {
        return try {
            val query = firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .whereEqualTo("inviteCode", inviteCode)
                .get()
                .await()
            if (query.isEmpty) {
                return Resource.Error("Invalid invite code")
            }
            val group = query.documents.first().toObject(Group::class.java)
            if (group != null) Resource.Success(group)
            else Resource.Error("Group not found")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to join group")
        }
    }

    override suspend fun leaveGroup(groupId: String): Resource<Unit> {
        return try {
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to leave group")
        }
    }

    override suspend fun deleteGroup(groupId: String): Resource<Unit> {
        return try {
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to delete group")
        }
    }

    override suspend fun updateGroup(groupId: String, name: String, description: String): Resource<Group> {
        return try {
            val updates = hashMapOf<String, Any>(
                "name" to name,
                "description" to description
            )
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .update(updates)
                .await()
            val doc = firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .get()
                .await()
            val group = doc.toObject(Group::class.java)
            if (group != null) Resource.Success(group)
            else Resource.Error("Group not found")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update group")
        }
    }

    override fun observeGroupMembers(groupId: String): Flow<List<GroupMember>> = callbackFlow {
        val listener: ListenerRegistration = firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
            .document(groupId)
            .collection(AppConstants.FIRESTORE_COLLECTION_MEMBERS)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val members = snapshot?.toObjects(GroupMember::class.java) ?: emptyList()
                trySend(members).isSuccess
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addMember(groupId: String, userId: String): Resource<Unit> {
        return try {
            val member = GroupMember(
                id = "$userId",
                userId = userId,
                groupId = groupId,
                role = MemberRole.MEMBER,
                joinedAt = System.currentTimeMillis()
            )
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .collection(AppConstants.FIRESTORE_COLLECTION_MEMBERS)
                .document(userId)
                .set(member)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to add member")
        }
    }

    override suspend fun removeMember(groupId: String, userId: String): Resource<Unit> {
        return try {
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .collection(AppConstants.FIRESTORE_COLLECTION_MEMBERS)
                .document(userId)
                .delete()
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to remove member")
        }
    }

    override suspend fun updateMemberRole(groupId: String, userId: String, role: MemberRole): Resource<Unit> {
        return try {
            firestore.collection(AppConstants.FIRESTORE_COLLECTION_GROUPS)
                .document(groupId)
                .collection(AppConstants.FIRESTORE_COLLECTION_MEMBERS)
                .document(userId)
                .update("role", role.name)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update member role")
        }
    }
}
