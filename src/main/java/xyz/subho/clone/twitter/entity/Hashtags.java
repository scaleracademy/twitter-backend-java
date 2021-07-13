/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.subho.clone.twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Hashtags")
@Table(
    name = "hashtags",
    indexes = {@Index(columnList = "tag")})
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@NoArgsConstructor
public class Hashtags implements Serializable {

  private static final long serialVersionUID = 9295165523670L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "tag", unique = true, nullable = false, length = 240)
  @NaturalId
  private String tag;

  @Column(name = "recent_post_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private Long recentPostCount = 0L;

  @OneToMany(
      mappedBy = "hashtags",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  @JsonIgnore
  private List<HashtagPosts> hashtagPosts = new ArrayList<>();

  @CreationTimestamp private Date createdAt = new Date();

  @UpdateTimestamp private Date updatedAt = new Date();

  /** @param tag */
  public Hashtags(String tag) {
    this.tag = tag;
    recentPostCount = 0L;
  }
  
  public long incrementRecentPostCount() {
	  return ++recentPostCount;
  }
}
