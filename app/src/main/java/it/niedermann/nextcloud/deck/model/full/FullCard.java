package it.niedermann.nextcloud.deck.model.full;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

import it.niedermann.nextcloud.deck.model.Attachment;
import it.niedermann.nextcloud.deck.model.Card;
import it.niedermann.nextcloud.deck.model.JoinCardWithLabel;
import it.niedermann.nextcloud.deck.model.JoinCardWithUser;
import it.niedermann.nextcloud.deck.model.Label;
import it.niedermann.nextcloud.deck.model.User;
import it.niedermann.nextcloud.deck.model.interfaces.IRemoteEntity;

public class FullCard implements IRemoteEntity {
    @Embedded
    public Card card;

    @Relation(entity = JoinCardWithLabel.class, parentColumn = "localId", entityColumn = "cardId", projection = "labelId")
    public List<Long> labelIDs;

    @Ignore
    public List<Label> labels;


    @Relation(entity = JoinCardWithUser.class, parentColumn = "localId", entityColumn = "cardId", projection = "userId")
    public List<Long> assignedUserIDs;

    @Ignore
    public List<User> assignedUsers;

    @Relation(parentColumn = "userId", entityColumn = "localId")
    public List<User> owner;

    @Relation(parentColumn = "localId", entityColumn = "cardId")
    public List<Attachment> attachments;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public List<User> getOwner() {
        return owner;
    }

    public List<Long> getAssignedUserIDs() {
        return assignedUserIDs;
    }

    public void setAssignedUserIDs(List<Long> assignedUserIDs) {
        this.assignedUserIDs = assignedUserIDs;
    }

    public List<Long> getLabelIDs() {
        return labelIDs;
    }

    public void setLabelIDs(List<Long> labelIDs) {
        this.labelIDs = labelIDs;
    }

    public void setOwner(User owner) {
        List<User> user = new ArrayList<>();
        user.add(owner);
        this.owner = user;
    }

    public void setOwner(List<User> owner) {
        this.owner = owner;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Ignore
    @Override
    public IRemoteEntity getEntity() {
        return card;
    }

    @Override
    public String toString() {
        return "FullCard{" +
                "card=" + card +
                ", labelIDs=" + labelIDs +
                ", labels=" + labels +
                ", assignedUserIDs=" + assignedUserIDs +
                ", assignedUsers=" + assignedUsers +
                ", owner=" + owner +
                ", attachments=" + attachments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FullCard fullCard = (FullCard) o;

        if (card != null ? !card.equals(fullCard.card) : fullCard.card != null) return false;
        if (labelIDs != null ? !labelIDs.equals(fullCard.labelIDs) : fullCard.labelIDs != null)
            return false;
        if (labels != null ? !labels.equals(fullCard.labels) : fullCard.labels != null)
            return false;
        if (assignedUserIDs != null ? !assignedUserIDs.equals(fullCard.assignedUserIDs) : fullCard.assignedUserIDs != null)
            return false;
        if (assignedUsers != null ? !assignedUsers.equals(fullCard.assignedUsers) : fullCard.assignedUsers != null)
            return false;
        if (owner != null ? !owner.equals(fullCard.owner) : fullCard.owner != null) return false;
        return attachments != null ? attachments.equals(fullCard.attachments) : fullCard.attachments == null;
    }

    @Override
    public int hashCode() {
        int result = card != null ? card.hashCode() : 0;
        result = 31 * result + (labelIDs != null ? labelIDs.hashCode() : 0);
        result = 31 * result + (labels != null ? labels.hashCode() : 0);
        result = 31 * result + (assignedUserIDs != null ? assignedUserIDs.hashCode() : 0);
        result = 31 * result + (assignedUsers != null ? assignedUsers.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (attachments != null ? attachments.hashCode() : 0);
        return result;
    }
}
