package de.essigt.bos.boswatch.business.fms.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "latest_fms")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class FMSLatest extends FMS {

}
