package ir.millennium.sampleprojectcompose.data.model.local.aboutMe

data class UserProfileEntity(
    val image: Int,
    val fullName: Int,
    val socialNetwork: List<UserProfileSocialNetworkEntity>
)
