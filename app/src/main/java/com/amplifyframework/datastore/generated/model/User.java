package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users")
public final class User implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField IDENTITY_ID = field("identityId");
  public static final QueryField CREATED_AT = field("createdAt");
  public static final QueryField UPDATED_AT = field("updatedAt");
  public static final QueryField FIRSTNAME = field("firstname");
  public static final QueryField LASTNAME = field("lastname");
  public static final QueryField EMAIL = field("email");
  public static final QueryField PASSWORD = field("password");
  public static final QueryField PICTURE_NAME = field("pictureName");
  public static final QueryField PICTURE_URL = field("pictureUrl");
  public static final QueryField NOTIFICATION = field("notification");
  public static final QueryField ROLE = field("role");
  public static final QueryField GROUP = field("group");
  public static final QueryField LIMITED_STORAGE = field("limitedStorage");
  public static final QueryField STORAGE_SPACE_USED = field("storageSpaceUsed");
  public static final QueryField TOTAL_STORAGE_SPACE = field("totalStorageSpace");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String identityId;
  private final @ModelField(targetType="String") String createdAt;
  private final @ModelField(targetType="String") String updatedAt;
  private final @ModelField(targetType="String") String firstname;
  private final @ModelField(targetType="String") String lastname;
  private final @ModelField(targetType="String", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String password;
  private final @ModelField(targetType="String") String pictureName;
  private final @ModelField(targetType="String") String pictureUrl;
  private final @ModelField(targetType="Boolean") Boolean notification;
  private final @ModelField(targetType="String", isRequired = true) String role;
  private final @ModelField(targetType="ID") String group;
  private final @ModelField(targetType="Boolean") Boolean limitedStorage;
  private final @ModelField(targetType="Float") Float storageSpaceUsed;
  private final @ModelField(targetType="Float") Float totalStorageSpace;
  public String getId() {
      return id;
  }
  
  public String getIdentityId() {
      return identityId;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUpdatedAt() {
      return updatedAt;
  }
  
  public String getFirstname() {
      return firstname;
  }
  
  public String getLastname() {
      return lastname;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getPassword() {
      return password;
  }
  
  public String getPictureName() {
      return pictureName;
  }
  
  public String getPictureUrl() {
      return pictureUrl;
  }
  
  public Boolean getNotification() {
      return notification;
  }
  
  public String getRole() {
      return role;
  }
  
  public String getGroup() {
      return group;
  }
  
  public Boolean getLimitedStorage() {
      return limitedStorage;
  }
  
  public Float getStorageSpaceUsed() {
      return storageSpaceUsed;
  }
  
  public Float getTotalStorageSpace() {
      return totalStorageSpace;
  }
  
  private User(String id, String identityId, String createdAt, String updatedAt, String firstname, String lastname, String email, String password, String pictureName, String pictureUrl, Boolean notification, String role, String group, Boolean limitedStorage, Float storageSpaceUsed, Float totalStorageSpace) {
    this.id = id;
    this.identityId = identityId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.pictureName = pictureName;
    this.pictureUrl = pictureUrl;
    this.notification = notification;
    this.role = role;
    this.group = group;
    this.limitedStorage = limitedStorage;
    this.storageSpaceUsed = storageSpaceUsed;
    this.totalStorageSpace = totalStorageSpace;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getIdentityId(), user.getIdentityId()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt()) &&
              ObjectsCompat.equals(getFirstname(), user.getFirstname()) &&
              ObjectsCompat.equals(getLastname(), user.getLastname()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getPassword(), user.getPassword()) &&
              ObjectsCompat.equals(getPictureName(), user.getPictureName()) &&
              ObjectsCompat.equals(getPictureUrl(), user.getPictureUrl()) &&
              ObjectsCompat.equals(getNotification(), user.getNotification()) &&
              ObjectsCompat.equals(getRole(), user.getRole()) &&
              ObjectsCompat.equals(getGroup(), user.getGroup()) &&
              ObjectsCompat.equals(getLimitedStorage(), user.getLimitedStorage()) &&
              ObjectsCompat.equals(getStorageSpaceUsed(), user.getStorageSpaceUsed()) &&
              ObjectsCompat.equals(getTotalStorageSpace(), user.getTotalStorageSpace());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getIdentityId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getFirstname())
      .append(getLastname())
      .append(getEmail())
      .append(getPassword())
      .append(getPictureName())
      .append(getPictureUrl())
      .append(getNotification())
      .append(getRole())
      .append(getGroup())
      .append(getLimitedStorage())
      .append(getStorageSpaceUsed())
      .append(getTotalStorageSpace())
      .toString()
      .hashCode();
  }
  
  public static IdentityIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static User justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new User(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      identityId,
      createdAt,
      updatedAt,
      firstname,
      lastname,
      email,
      password,
      pictureName,
      pictureUrl,
      notification,
      role,
      group,
      limitedStorage,
      storageSpaceUsed,
      totalStorageSpace);
  }
  public interface IdentityIdStep {
    EmailStep identityId(String identityId);
  }
  

  public interface EmailStep {
    PasswordStep email(String email);
  }
  

  public interface PasswordStep {
    RoleStep password(String password);
  }
  

  public interface RoleStep {
    BuildStep role(String role);
  }
  

  public interface BuildStep {
    User build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep createdAt(String createdAt);
    BuildStep updatedAt(String updatedAt);
    BuildStep firstname(String firstname);
    BuildStep lastname(String lastname);
    BuildStep pictureName(String pictureName);
    BuildStep pictureUrl(String pictureUrl);
    BuildStep notification(Boolean notification);
    BuildStep group(String group);
    BuildStep limitedStorage(Boolean limitedStorage);
    BuildStep storageSpaceUsed(Float storageSpaceUsed);
    BuildStep totalStorageSpace(Float totalStorageSpace);
  }
  

  public static class Builder implements IdentityIdStep, EmailStep, PasswordStep, RoleStep, BuildStep {
    private String id;
    private String identityId;
    private String email;
    private String password;
    private String role;
    private String createdAt;
    private String updatedAt;
    private String firstname;
    private String lastname;
    private String pictureName;
    private String pictureUrl;
    private Boolean notification;
    private String group;
    private Boolean limitedStorage;
    private Float storageSpaceUsed;
    private Float totalStorageSpace;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          identityId,
          createdAt,
          updatedAt,
          firstname,
          lastname,
          email,
          password,
          pictureName,
          pictureUrl,
          notification,
          role,
          group,
          limitedStorage,
          storageSpaceUsed,
          totalStorageSpace);
    }
    
    @Override
     public EmailStep identityId(String identityId) {
        Objects.requireNonNull(identityId);
        this.identityId = identityId;
        return this;
    }
    
    @Override
     public PasswordStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public RoleStep password(String password) {
        Objects.requireNonNull(password);
        this.password = password;
        return this;
    }
    
    @Override
     public BuildStep role(String role) {
        Objects.requireNonNull(role);
        this.role = role;
        return this;
    }
    
    @Override
     public BuildStep createdAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep updatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
    
    @Override
     public BuildStep firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }
    
    @Override
     public BuildStep lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }
    
    @Override
     public BuildStep pictureName(String pictureName) {
        this.pictureName = pictureName;
        return this;
    }
    
    @Override
     public BuildStep pictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
        return this;
    }
    
    @Override
     public BuildStep notification(Boolean notification) {
        this.notification = notification;
        return this;
    }
    
    @Override
     public BuildStep group(String group) {
        this.group = group;
        return this;
    }
    
    @Override
     public BuildStep limitedStorage(Boolean limitedStorage) {
        this.limitedStorage = limitedStorage;
        return this;
    }
    
    @Override
     public BuildStep storageSpaceUsed(Float storageSpaceUsed) {
        this.storageSpaceUsed = storageSpaceUsed;
        return this;
    }
    
    @Override
     public BuildStep totalStorageSpace(Float totalStorageSpace) {
        this.totalStorageSpace = totalStorageSpace;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String identityId, String createdAt, String updatedAt, String firstname, String lastname, String email, String password, String pictureName, String pictureUrl, Boolean notification, String role, String group, Boolean limitedStorage, Float storageSpaceUsed, Float totalStorageSpace) {
      super.id(id);
      super.identityId(identityId)
        .email(email)
        .password(password)
        .role(role)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .firstname(firstname)
        .lastname(lastname)
        .pictureName(pictureName)
        .pictureUrl(pictureUrl)
        .notification(notification)
        .group(group)
        .limitedStorage(limitedStorage)
        .storageSpaceUsed(storageSpaceUsed)
        .totalStorageSpace(totalStorageSpace);
    }
    
    @Override
     public CopyOfBuilder identityId(String identityId) {
      return (CopyOfBuilder) super.identityId(identityId);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
    
    @Override
     public CopyOfBuilder role(String role) {
      return (CopyOfBuilder) super.role(role);
    }
    
    @Override
     public CopyOfBuilder createdAt(String createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder updatedAt(String updatedAt) {
      return (CopyOfBuilder) super.updatedAt(updatedAt);
    }
    
    @Override
     public CopyOfBuilder firstname(String firstname) {
      return (CopyOfBuilder) super.firstname(firstname);
    }
    
    @Override
     public CopyOfBuilder lastname(String lastname) {
      return (CopyOfBuilder) super.lastname(lastname);
    }
    
    @Override
     public CopyOfBuilder pictureName(String pictureName) {
      return (CopyOfBuilder) super.pictureName(pictureName);
    }
    
    @Override
     public CopyOfBuilder pictureUrl(String pictureUrl) {
      return (CopyOfBuilder) super.pictureUrl(pictureUrl);
    }
    
    @Override
     public CopyOfBuilder notification(Boolean notification) {
      return (CopyOfBuilder) super.notification(notification);
    }
    
    @Override
     public CopyOfBuilder group(String group) {
      return (CopyOfBuilder) super.group(group);
    }
    
    @Override
     public CopyOfBuilder limitedStorage(Boolean limitedStorage) {
      return (CopyOfBuilder) super.limitedStorage(limitedStorage);
    }
    
    @Override
     public CopyOfBuilder storageSpaceUsed(Float storageSpaceUsed) {
      return (CopyOfBuilder) super.storageSpaceUsed(storageSpaceUsed);
    }
    
    @Override
     public CopyOfBuilder totalStorageSpace(Float totalStorageSpace) {
      return (CopyOfBuilder) super.totalStorageSpace(totalStorageSpace);
    }
  }
  
}
