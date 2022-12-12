package com.amplifyframework.datastore.generated.model;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.Objects;
import java.util.UUID;

/** This is an auto generated class representing the Task type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byTeam", fields = {"teamID","title"})
public final class Task implements Model {
  public static final QueryField ID = field("Task", "id");
  public static final QueryField TITLE = field("Task", "title");
  public static final QueryField STATE = field("Task", "state");
  public static final QueryField DATE_CREATED = field("Task", "dateCreated");
  public static final QueryField BODY = field("Task", "body");
  public static final QueryField TEAM = field("Task", "teamID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="TaskEnum", isRequired = true) TaskEnum state;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime dateCreated;
  private final @ModelField(targetType="String") String body;
  private final @ModelField(targetType="Team") @BelongsTo(targetName = "teamID", targetNames = {"teamID"}, type = Team.class) Team team;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public TaskEnum getState() {
      return state;
  }
  
  public Temporal.DateTime getDateCreated() {
      return dateCreated;
  }
  
  public String getBody() {
      return body;
  }
  
  public Team getTeam() {
      return team;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Task(String id, String title, TaskEnum state, Temporal.DateTime dateCreated, String body, Team team) {
    this.id = id;
    this.title = title;
    this.state = state;
    this.dateCreated = dateCreated;
    this.body = body;
    this.team = team;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Task task = (Task) obj;
      return ObjectsCompat.equals(getId(), task.getId()) &&
              ObjectsCompat.equals(getTitle(), task.getTitle()) &&
              ObjectsCompat.equals(getState(), task.getState()) &&
              ObjectsCompat.equals(getDateCreated(), task.getDateCreated()) &&
              ObjectsCompat.equals(getBody(), task.getBody()) &&
              ObjectsCompat.equals(getTeam(), task.getTeam()) &&
              ObjectsCompat.equals(getCreatedAt(), task.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), task.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getState())
      .append(getDateCreated())
      .append(getBody())
      .append(getTeam())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Task {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("dateCreated=" + String.valueOf(getDateCreated()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Task justId(String id) {
    return new Task(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      state,
      dateCreated,
      body,
      team);
  }
  public interface TitleStep {
    StateStep title(String title);
  }
  

  public interface StateStep {
    DateCreatedStep state(TaskEnum state);
  }
  

  public interface DateCreatedStep {
    BuildStep dateCreated(Temporal.DateTime dateCreated);
  }
  

  public interface BuildStep {
    Task build();
    BuildStep id(String id);
    BuildStep body(String body);
    BuildStep team(Team team);
  }
  

  public static class Builder implements TitleStep, StateStep, DateCreatedStep, BuildStep {
    private String id;
    private String title;
    private TaskEnum state;
    private Temporal.DateTime dateCreated;
    private String body;
    private Team team;
    @Override
     public Task build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Task(
          id,
          title,
          state,
          dateCreated,
          body,
          team);
    }
    
    @Override
     public StateStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public DateCreatedStep state(TaskEnum state) {
        Objects.requireNonNull(state);
        this.state = state;
        return this;
    }
    
    @Override
     public BuildStep dateCreated(Temporal.DateTime dateCreated) {
        Objects.requireNonNull(dateCreated);
        this.dateCreated = dateCreated;
        return this;
    }
    
    @Override
     public BuildStep body(String body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep team(Team team) {
        this.team = team;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String title, TaskEnum state, Temporal.DateTime dateCreated, String body, Team team) {
      super.id(id);
      super.title(title)
        .state(state)
        .dateCreated(dateCreated)
        .body(body)
        .team(team);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder state(TaskEnum state) {
      return (CopyOfBuilder) super.state(state);
    }
    
    @Override
     public CopyOfBuilder dateCreated(Temporal.DateTime dateCreated) {
      return (CopyOfBuilder) super.dateCreated(dateCreated);
    }
    
    @Override
     public CopyOfBuilder body(String body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
    }
  }
  
}
