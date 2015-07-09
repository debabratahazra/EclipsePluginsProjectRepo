package com.odcgroup.aaa.connector.domainmodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.odcgroup.aaa.connector.domainmodel.ids.ScriptDefinitionEntityId;


/**
 * Script Definition.
 * 
 * @author Michael Vorburger
 */
@Entity(name = "ScriptDefinition")
@Table(name = "script_definition_vw")
@IdClass(ScriptDefinitionEntityId.class)
public class ScriptDefinitionEntity
{
// NOTE: Map "attribute_dict_id" as Long and NOT as DictAttribute, else this leads to very weired OpenJPA bug/s (I've spent a night on it getting all confused, after making DictAttribute extend TranslateableAdapter)!     
//  @Id
//  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional=false)
//  @JoinColumn(name = "attribute_dict_id", nullable=false)
//  private DictAttribute attribute;

    @Id
    @Basic(optional = false)
    @Column(name = "attribute_dict_id", nullable=false)
    private long attribute;

    @Id
    @Column(name = "object_id", nullable=true)   // On most entities (e.g. Denomination) actually never NULL, but possible e.g. for ScriptDefinition
    private Long object;

    @Id
    @Basic(optional = false)
    @Column(name = "rank_n", nullable=false)     // Never NULL
    private int rank;

    @Id
    @Basic(optional = false)
    @Column(name = "nature_e", nullable=false)   // Never NULL
    private int nature;
    
    @Column(name="definition_c", nullable=true)  // Usually not NULL, but possible...
    private String definition;
    
    public String getDefinition() {
        return definition;
    }
    
    // NOTE: action_e/dim_entity_dict_id/script_ent_ref_dict_id intentionally not mapped, it seems useless?
}
