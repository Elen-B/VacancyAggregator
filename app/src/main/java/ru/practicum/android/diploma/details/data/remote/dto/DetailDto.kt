package ru.practicum.android.diploma.details.data.remote.dto


import com.google.gson.annotations.SerializedName

data class DetailDto(
    @SerializedName("accept_handicapped")
    val acceptHandicapped: Boolean,
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    @SerializedName("accept_kids")
    val acceptKids: Boolean,
    @SerializedName("accept_temporary")
    val acceptTemporary: Boolean,
    @SerializedName("address")
    val address: Address,
    @SerializedName("allow_messages")
    val allowMessages: Boolean,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String,
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("area")
    val area: Area,
    @SerializedName("billing_type")
    val billingType: BillingType,
    @SerializedName("branded_description")
    val brandedDescription: String,
    @SerializedName("branded_template")
    val brandedTemplate: BrandedTemplate,
    @SerializedName("can_upgrade_billing_type")
    val canUpgradeBillingType: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("contacts")
    val contacts: Contacts,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("department")
    val department: Department,
    @SerializedName("description")
    val description: String,
    @SerializedName("driver_license_types")
    val driverLicenseTypes: List<DriverLicenseType>,
    @SerializedName("employer")
    val employer: Employer,
    @SerializedName("employment")
    val employment: Employment,
    @SerializedName("experience")
    val experience: Experience,
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("has_test")
    val hasTest: Boolean,
    @SerializedName("hidden")
    val hidden: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("initial_created_at")
    val initialCreatedAt: String,
    @SerializedName("insider_interview")
    val insiderInterview: InsiderInterview,
    @SerializedName("key_skills")
    val keySkills: List<KeySkill>,
    @SerializedName("languages")
    val languages: List<Language>,
    @SerializedName("manager")
    val manager: Manager,
    @SerializedName("name")
    val name: String,
    @SerializedName("premium")
    val premium: Boolean,
    @SerializedName("previous_id")
    val previousId: String,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRole>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("response_letter_required")
    val responseLetterRequired: Boolean,
    @SerializedName("response_notifications")
    val responseNotifications: Boolean,
    @SerializedName("response_url")
    val responseUrl: Any,
    @SerializedName("salary")
    val salary: Salary,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("test")
    val test: Test,
    @SerializedName("type")
    val type: Type,
    @SerializedName("video_vacancy")
    val videoVacancy: VideoVacancy,
    @SerializedName("working_days")
    val workingDays: List<WorkingDay>,
    @SerializedName("working_time_intervals")
    val workingTimeIntervals: List<WorkingTimeInterval>,
    @SerializedName("working_time_modes")
    val workingTimeModes: List<WorkingTimeMode>
)