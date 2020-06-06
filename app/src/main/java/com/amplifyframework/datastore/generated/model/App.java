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

/** This is an auto generated class representing the App type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Apps")
public final class App implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField GROUP_ID = field("groupId");
  public static final QueryField SUBSCRIPTION = field("subscription");
  public static final QueryField PACK = field("pack");
  public static final QueryField CUSTOM = field("custom");
  public static final QueryField HEADER_COLOR = field("headerColor");
  public static final QueryField ICONE_URL = field("iconeUrl");
  public static final QueryField FONT = field("font");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String groupId;
  private final @ModelField(targetType="String", isRequired = true) String subscription;
  private final @ModelField(targetType="Int", isRequired = true) Integer pack;
  private final @ModelField(targetType="Boolean") Boolean custom;
  private final @ModelField(targetType="String") String headerColor;
  private final @ModelField(targetType="String") String iconeUrl;
  private final @ModelField(targetType="String") String font;
  public String getId() {
      return id;
  }
  
  public String getGroupId() {
      return groupId;
  }
  
  public String getSubscription() {
      return subscription;
  }
  
  public Integer getPack() {
      return pack;
  }
  
  public Boolean getCustom() {
      return custom;
  }
  
  public String getHeaderColor() {
      return headerColor;
  }
  
  public String getIconeUrl() {
      return iconeUrl;
  }
  
  public String getFont() {
      return font;
  }
  
  private App(String id, String groupId, String subscription, Integer pack, Boolean custom, String headerColor, String iconeUrl, String font) {
    this.id = id;
    this.groupId = groupId;
    this.subscription = subscription;
    this.pack = pack;
    this.custom = custom;
    this.headerColor = headerColor;
    this.iconeUrl = iconeUrl;
    this.font = font;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      App app = (App) obj;
      return ObjectsCompat.equals(getId(), app.getId()) &&
              ObjectsCompat.equals(getGroupId(), app.getGroupId()) &&
              ObjectsCompat.equals(getSubscription(), app.getSubscription()) &&
              ObjectsCompat.equals(getPack(), app.getPack()) &&
              ObjectsCompat.equals(getCustom(), app.getCustom()) &&
              ObjectsCompat.equals(getHeaderColor(), app.getHeaderColor()) &&
              ObjectsCompat.equals(getIconeUrl(), app.getIconeUrl()) &&
              ObjectsCompat.equals(getFont(), app.getFont());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getGroupId())
      .append(getSubscription())
      .append(getPack())
      .append(getCustom())
      .append(getHeaderColor())
      .append(getIconeUrl())
      .append(getFont())
      .toString()
      .hashCode();
  }
  
  public static GroupIdStep builder() {
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
  public static App justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new App(
      id,
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
      groupId,
      subscription,
      pack,
      custom,
      headerColor,
      iconeUrl,
      font);
  }
  public interface GroupIdStep {
    SubscriptionStep groupId(String groupId);
  }
  

  public interface SubscriptionStep {
    PackStep subscription(String subscription);
  }
  

  public interface PackStep {
    BuildStep pack(Integer pack);
  }
  

  public interface BuildStep {
    App build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep custom(Boolean custom);
    BuildStep headerColor(String headerColor);
    BuildStep iconeUrl(String iconeUrl);
    BuildStep font(String font);
  }
  

  public static class Builder implements GroupIdStep, SubscriptionStep, PackStep, BuildStep {
    private String id;
    private String groupId;
    private String subscription;
    private Integer pack;
    private Boolean custom;
    private String headerColor;
    private String iconeUrl;
    private String font;
    @Override
     public App build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new App(
          id,
          groupId,
          subscription,
          pack,
          custom,
          headerColor,
          iconeUrl,
          font);
    }
    
    @Override
     public SubscriptionStep groupId(String groupId) {
        Objects.requireNonNull(groupId);
        this.groupId = groupId;
        return this;
    }
    
    @Override
     public PackStep subscription(String subscription) {
        Objects.requireNonNull(subscription);
        this.subscription = subscription;
        return this;
    }
    
    @Override
     public BuildStep pack(Integer pack) {
        Objects.requireNonNull(pack);
        this.pack = pack;
        return this;
    }
    
    @Override
     public BuildStep custom(Boolean custom) {
        this.custom = custom;
        return this;
    }
    
    @Override
     public BuildStep headerColor(String headerColor) {
        this.headerColor = headerColor;
        return this;
    }
    
    @Override
     public BuildStep iconeUrl(String iconeUrl) {
        this.iconeUrl = iconeUrl;
        return this;
    }
    
    @Override
     public BuildStep font(String font) {
        this.font = font;
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
    private CopyOfBuilder(String id, String groupId, String subscription, Integer pack, Boolean custom, String headerColor, String iconeUrl, String font) {
      super.id(id);
      super.groupId(groupId)
        .subscription(subscription)
        .pack(pack)
        .custom(custom)
        .headerColor(headerColor)
        .iconeUrl(iconeUrl)
        .font(font);
    }
    
    @Override
     public CopyOfBuilder groupId(String groupId) {
      return (CopyOfBuilder) super.groupId(groupId);
    }
    
    @Override
     public CopyOfBuilder subscription(String subscription) {
      return (CopyOfBuilder) super.subscription(subscription);
    }
    
    @Override
     public CopyOfBuilder pack(Integer pack) {
      return (CopyOfBuilder) super.pack(pack);
    }
    
    @Override
     public CopyOfBuilder custom(Boolean custom) {
      return (CopyOfBuilder) super.custom(custom);
    }
    
    @Override
     public CopyOfBuilder headerColor(String headerColor) {
      return (CopyOfBuilder) super.headerColor(headerColor);
    }
    
    @Override
     public CopyOfBuilder iconeUrl(String iconeUrl) {
      return (CopyOfBuilder) super.iconeUrl(iconeUrl);
    }
    
    @Override
     public CopyOfBuilder font(String font) {
      return (CopyOfBuilder) super.font(font);
    }
  }
  
}
