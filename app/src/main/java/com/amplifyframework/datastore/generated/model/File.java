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

/** This is an auto generated class representing the File type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Files")
public final class File implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField CREATED_AT = field("createdAt");
  public static final QueryField UPDATED_AT = field("updatedAt");
  public static final QueryField NAME = field("name");
  public static final QueryField CONTENT = field("content");
  public static final QueryField OWNER = field("owner");
  public static final QueryField IS_PROTECTED = field("isProtected");
  public static final QueryField PASSWORD = field("password");
  public static final QueryField PARENT = field("parent");
  public static final QueryField SIZE = field("size");
  public static final QueryField MIME_TYPE = field("mimeType");
  public static final QueryField TYPE = field("type");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String createdAt;
  private final @ModelField(targetType="String") String updatedAt;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String content;
  private final @ModelField(targetType="ID", isRequired = true) String owner;
  private final @ModelField(targetType="Boolean") Boolean isProtected;
  private final @ModelField(targetType="String") String password;
  private final @ModelField(targetType="ID", isRequired = true) String parent;
  private final @ModelField(targetType="Float") Float size;
  private final @ModelField(targetType="String") String mimeType;
  private final @ModelField(targetType="String") String type;
  public String getId() {
      return id;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUpdatedAt() {
      return updatedAt;
  }
  
  public String getName() {
      return name;
  }
  
  public String getContent() {
      return content;
  }
  
  public String getOwner() {
      return owner;
  }
  
  public Boolean getIsProtected() {
      return isProtected;
  }
  
  public String getPassword() {
      return password;
  }
  
  public String getParent() {
      return parent;
  }
  
  public Float getSize() {
      return size;
  }
  
  public String getMimeType() {
      return mimeType;
  }
  
  public String getType() {
      return type;
  }
  
  private File(String id, String createdAt, String updatedAt, String name, String content, String owner, Boolean isProtected, String password, String parent, Float size, String mimeType, String type) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.name = name;
    this.content = content;
    this.owner = owner;
    this.isProtected = isProtected;
    this.password = password;
    this.parent = parent;
    this.size = size;
    this.mimeType = mimeType;
    this.type = type;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      File file = (File) obj;
      return ObjectsCompat.equals(getId(), file.getId()) &&
              ObjectsCompat.equals(getCreatedAt(), file.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), file.getUpdatedAt()) &&
              ObjectsCompat.equals(getName(), file.getName()) &&
              ObjectsCompat.equals(getContent(), file.getContent()) &&
              ObjectsCompat.equals(getOwner(), file.getOwner()) &&
              ObjectsCompat.equals(getIsProtected(), file.getIsProtected()) &&
              ObjectsCompat.equals(getPassword(), file.getPassword()) &&
              ObjectsCompat.equals(getParent(), file.getParent()) &&
              ObjectsCompat.equals(getSize(), file.getSize()) &&
              ObjectsCompat.equals(getMimeType(), file.getMimeType()) &&
              ObjectsCompat.equals(getType(), file.getType());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getName())
      .append(getContent())
      .append(getOwner())
      .append(getIsProtected())
      .append(getPassword())
      .append(getParent())
      .append(getSize())
      .append(getMimeType())
      .append(getType())
      .toString()
      .hashCode();
  }
  
  public static NameStep builder() {
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
  public static File justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new File(
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      createdAt,
      updatedAt,
      name,
      content,
      owner,
      isProtected,
      password,
      parent,
      size,
      mimeType,
      type);
  }
  public interface NameStep {
    OwnerStep name(String name);
  }
  

  public interface OwnerStep {
    ParentStep owner(String owner);
  }
  

  public interface ParentStep {
    BuildStep parent(String parent);
  }
  

  public interface BuildStep {
    File build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep createdAt(String createdAt);
    BuildStep updatedAt(String updatedAt);
    BuildStep content(String content);
    BuildStep isProtected(Boolean isProtected);
    BuildStep password(String password);
    BuildStep size(Float size);
    BuildStep mimeType(String mimeType);
    BuildStep type(String type);
  }
  

  public static class Builder implements NameStep, OwnerStep, ParentStep, BuildStep {
    private String id;
    private String name;
    private String owner;
    private String parent;
    private String createdAt;
    private String updatedAt;
    private String content;
    private Boolean isProtected;
    private String password;
    private Float size;
    private String mimeType;
    private String type;
    @Override
     public File build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new File(
          id,
          createdAt,
          updatedAt,
          name,
          content,
          owner,
          isProtected,
          password,
          parent,
          size,
          mimeType,
          type);
    }
    
    @Override
     public OwnerStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public ParentStep owner(String owner) {
        Objects.requireNonNull(owner);
        this.owner = owner;
        return this;
    }
    
    @Override
     public BuildStep parent(String parent) {
        Objects.requireNonNull(parent);
        this.parent = parent;
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
     public BuildStep content(String content) {
        this.content = content;
        return this;
    }
    
    @Override
     public BuildStep isProtected(Boolean isProtected) {
        this.isProtected = isProtected;
        return this;
    }
    
    @Override
     public BuildStep password(String password) {
        this.password = password;
        return this;
    }
    
    @Override
     public BuildStep size(Float size) {
        this.size = size;
        return this;
    }
    
    @Override
     public BuildStep mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }
    
    @Override
     public BuildStep type(String type) {
        this.type = type;
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
    private CopyOfBuilder(String id, String createdAt, String updatedAt, String name, String content, String owner, Boolean isProtected, String password, String parent, Float size, String mimeType, String type) {
      super.id(id);
      super.name(name)
        .owner(owner)
        .parent(parent)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .content(content)
        .isProtected(isProtected)
        .password(password)
        .size(size)
        .mimeType(mimeType)
        .type(type);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder owner(String owner) {
      return (CopyOfBuilder) super.owner(owner);
    }
    
    @Override
     public CopyOfBuilder parent(String parent) {
      return (CopyOfBuilder) super.parent(parent);
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
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder isProtected(Boolean isProtected) {
      return (CopyOfBuilder) super.isProtected(isProtected);
    }
    
    @Override
     public CopyOfBuilder password(String password) {
      return (CopyOfBuilder) super.password(password);
    }
    
    @Override
     public CopyOfBuilder size(Float size) {
      return (CopyOfBuilder) super.size(size);
    }
    
    @Override
     public CopyOfBuilder mimeType(String mimeType) {
      return (CopyOfBuilder) super.mimeType(mimeType);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
  }
  
}
