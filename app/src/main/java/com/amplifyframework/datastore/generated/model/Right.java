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
  public static final QueryField READ = field("read");
  public static final QueryField EDIT = field("edit");
  public static final QueryField DOCUMENT_ID = field("documentId");
  public static final QueryField USER = field("user");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean read;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean edit;
  private final @ModelField(targetType="ID", isRequired = true) String documentId;
  private final @ModelField(targetType="ID", isRequired = true) String user;
  public String getId() {
      return id;
  }
  
  public Boolean getRead() {
      return read;
  }
  
  public Boolean getEdit() {
      return edit;
  }
  
  public String getDocumentId() {
      return documentId;
  }
  
  public String getUser() {
      return user;
  }
  
  private Right(String id, Boolean read, Boolean edit, String documentId, String user) {
    this.id = id;
    this.read = read;
    this.edit = edit;
    this.documentId = documentId;
    this.user = user;
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
              ObjectsCompat.equals(getRead(), right.getRead()) &&
              ObjectsCompat.equals(getEdit(), right.getEdit()) &&
              ObjectsCompat.equals(getDocumentId(), right.getDocumentId()) &&
              ObjectsCompat.equals(getUser(), right.getUser());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getRead())
      .append(getEdit())
      .append(getDocumentId())
      .append(getUser())
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      read,
      edit,
      documentId,
      user);
  }
  public interface ReadStep {
    EditStep read(Boolean read);
  }
  

  public interface EditStep {
    DocumentIdStep edit(Boolean edit);
  }
  

  public interface DocumentIdStep {
    UserStep documentId(String documentId);
  }
  

  public interface UserStep {
    BuildStep user(String user);
  }
  

  public interface BuildStep {
    Right build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements ReadStep, EditStep, DocumentIdStep, UserStep, BuildStep {
    private String id;
    private Boolean read;
    private Boolean edit;
    private String documentId;
    private String user;
    @Override
     public Right build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Right(
          id,
          read,
          edit,
          documentId,
          user);
    }
    
    @Override
     public EditStep read(Boolean read) {
        Objects.requireNonNull(read);
        this.read = read;
        return this;
    }
    
    @Override
     public DocumentIdStep edit(Boolean edit) {
        Objects.requireNonNull(edit);
        this.edit = edit;
        return this;
    }
    
    @Override
     public UserStep documentId(String documentId) {
        Objects.requireNonNull(documentId);
        this.documentId = documentId;
        return this;
    }
    
    @Override
     public BuildStep user(String user) {
        Objects.requireNonNull(user);
        this.user = user;
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
    private CopyOfBuilder(String id, Boolean read, Boolean edit, String documentId, String user) {
      super.id(id);
      super.read(read)
        .edit(edit)
        .documentId(documentId)
        .user(user);
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
     public CopyOfBuilder documentId(String documentId) {
      return (CopyOfBuilder) super.documentId(documentId);
    }
    
    @Override
     public CopyOfBuilder user(String user) {
      return (CopyOfBuilder) super.user(user);
    }
  }
  
}
