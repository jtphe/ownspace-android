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

/** This is an auto generated class representing the Right type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Rights")
public final class Right implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField CREATED_AT = field("createdAt");
  public static final QueryField UPDATED_AT = field("updatedAt");
  public static final QueryField READ = field("read");
  public static final QueryField EDIT = field("edit");
  public static final QueryField DOCUMENT = field("document");
  public static final QueryField USER = field("user");
  public static final QueryField FIRSTNAME = field("firstname");
  public static final QueryField LASTNAME = field("lastname");
  public static final QueryField EMAIL = field("email");
  public static final QueryField TYPE = field("type");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String createdAt;
  private final @ModelField(targetType="String") String updatedAt;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean read;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean edit;
  private final @ModelField(targetType="ID", isRequired = true) String document;
  private final @ModelField(targetType="ID", isRequired = true) String user;
  private final @ModelField(targetType="String") String firstname;
  private final @ModelField(targetType="String") String lastname;
  private final @ModelField(targetType="String") String email;
  private final @ModelField(targetType="String", isRequired = true) String type;
  public String getId() {
      return id;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getUpdatedAt() {
      return updatedAt;
  }
  
  public Boolean getRead() {
      return read;
  }
  
  public Boolean getEdit() {
      return edit;
  }
  
  public String getDocument() {
      return document;
  }
  
  public String getUser() {
      return user;
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
  
  public String getType() {
      return type;
  }
  
  private Right(String id, String createdAt, String updatedAt, Boolean read, Boolean edit, String document, String user, String firstname, String lastname, String email, String type) {
    this.id = id;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.read = read;
    this.edit = edit;
    this.document = document;
    this.user = user;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.type = type;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Right right = (Right) obj;
      return ObjectsCompat.equals(getId(), right.getId()) &&
              ObjectsCompat.equals(getCreatedAt(), right.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), right.getUpdatedAt()) &&
              ObjectsCompat.equals(getRead(), right.getRead()) &&
              ObjectsCompat.equals(getEdit(), right.getEdit()) &&
              ObjectsCompat.equals(getDocument(), right.getDocument()) &&
              ObjectsCompat.equals(getUser(), right.getUser()) &&
              ObjectsCompat.equals(getFirstname(), right.getFirstname()) &&
              ObjectsCompat.equals(getLastname(), right.getLastname()) &&
              ObjectsCompat.equals(getEmail(), right.getEmail()) &&
              ObjectsCompat.equals(getType(), right.getType());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getRead())
      .append(getEdit())
      .append(getDocument())
      .append(getUser())
      .append(getFirstname())
      .append(getLastname())
      .append(getEmail())
      .append(getType())
      .toString()
      .hashCode();
  }
  
  public static ReadStep builder() {
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
  public static Right justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Right(
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      createdAt,
      updatedAt,
      read,
      edit,
      document,
      user,
      firstname,
      lastname,
      email,
      type);
  }
  public interface ReadStep {
    EditStep read(Boolean read);
  }
  

  public interface EditStep {
    DocumentStep edit(Boolean edit);
  }
  

  public interface DocumentStep {
    UserStep document(String document);
  }
  

  public interface UserStep {
    TypeStep user(String user);
  }
  

  public interface TypeStep {
    BuildStep type(String type);
  }
  

  public interface BuildStep {
    Right build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep createdAt(String createdAt);
    BuildStep updatedAt(String updatedAt);
    BuildStep firstname(String firstname);
    BuildStep lastname(String lastname);
    BuildStep email(String email);
  }
  

  public static class Builder implements ReadStep, EditStep, DocumentStep, UserStep, TypeStep, BuildStep {
    private String id;
    private Boolean read;
    private Boolean edit;
    private String document;
    private String user;
    private String type;
    private String createdAt;
    private String updatedAt;
    private String firstname;
    private String lastname;
    private String email;
    @Override
     public Right build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Right(
          id,
          createdAt,
          updatedAt,
          read,
          edit,
          document,
          user,
          firstname,
          lastname,
          email,
          type);
    }
    
    @Override
     public EditStep read(Boolean read) {
        Objects.requireNonNull(read);
        this.read = read;
        return this;
    }
    
    @Override
     public DocumentStep edit(Boolean edit) {
        Objects.requireNonNull(edit);
        this.edit = edit;
        return this;
    }
    
    @Override
     public UserStep document(String document) {
        Objects.requireNonNull(document);
        this.document = document;
        return this;
    }
    
    @Override
     public TypeStep user(String user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep type(String type) {
        Objects.requireNonNull(type);
        this.type = type;
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
     public BuildStep email(String email) {
        this.email = email;
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
    private CopyOfBuilder(String id, String createdAt, String updatedAt, Boolean read, Boolean edit, String document, String user, String firstname, String lastname, String email, String type) {
      super.id(id);
      super.read(read)
        .edit(edit)
        .document(document)
        .user(user)
        .type(type)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .firstname(firstname)
        .lastname(lastname)
        .email(email);
    }
    
    @Override
     public CopyOfBuilder read(Boolean read) {
      return (CopyOfBuilder) super.read(read);
    }
    
    @Override
     public CopyOfBuilder edit(Boolean edit) {
      return (CopyOfBuilder) super.edit(edit);
    }
    
    @Override
     public CopyOfBuilder document(String document) {
      return (CopyOfBuilder) super.document(document);
    }
    
    @Override
     public CopyOfBuilder user(String user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
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
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
  }
  
}
