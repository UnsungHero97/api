/*
 * Copyright (C) 2018. OpenLattice, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You can contact the owner of the copyright at support@openlattice.com
 */

package com.openlattice.edm.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openlattice.authorization.securable.AbstractSchemaAssociatedSecurableType;
import com.openlattice.authorization.securable.SecurableObjectType;
import com.openlattice.client.serialization.SerializationConstants;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;

/**
 * @author Matthew Tamayo-Rios &lt;matthew@openlattice.com&gt;
 */
public class PropertyType extends AbstractSchemaAssociatedSecurableType {

    protected final   boolean              multiValued;
    protected         EdmPrimitiveTypeKind datatype;
    protected         boolean              piiField;
    protected         Analyzer             analyzer;
    private transient int                  h = 0;

    @JsonCreator
    public PropertyType(
            @JsonProperty( SerializationConstants.ID_FIELD ) Optional<UUID> id,
            @JsonProperty( SerializationConstants.TYPE_FIELD ) FullQualifiedName fqn,
            @JsonProperty( SerializationConstants.TITLE_FIELD ) String title,
            @JsonProperty( SerializationConstants.DESCRIPTION_FIELD ) Optional<String> description,
            @JsonProperty( SerializationConstants.SCHEMAS ) Set<FullQualifiedName> schemas,
            @JsonProperty( SerializationConstants.DATATYPE_FIELD ) EdmPrimitiveTypeKind datatype,
            @JsonProperty( SerializationConstants.PII_FIELD ) Optional<Boolean> piiField,
            @JsonProperty( SerializationConstants.MULTI_VALUED ) Optional<Boolean> multiValued,
            @JsonProperty( SerializationConstants.ANALYZER ) Optional<Analyzer> analyzer ) {
        super(
                id,
                fqn,
                title,
                description,
                schemas );
        this.datatype = datatype;
        this.piiField = piiField.orElse( false );
        this.multiValued = multiValued.orElse( true );
        this.analyzer = analyzer.orElse( Analyzer.STANDARD );
    }

    public PropertyType(
            UUID id,
            FullQualifiedName fqn,
            String title,
            Optional<String> description,
            Set<FullQualifiedName> schemas,
            EdmPrimitiveTypeKind datatype,
            Optional<Boolean> piiField,
            Optional<Analyzer> analyzer ) {
        this( Optional.of( id ), fqn, title, description, schemas, datatype, piiField, Optional.empty(), analyzer );
    }

    public PropertyType(
            UUID id,
            FullQualifiedName fqn,
            String title,
            Optional<String> description,
            Set<FullQualifiedName> schemas,
            EdmPrimitiveTypeKind datatype,
            Optional<Boolean> piiField ) {
        this( Optional.of( id ),
                fqn,
                title,
                description,
                schemas,
                datatype,
                piiField,
                Optional.empty(),
                Optional.empty() );
    }

    public PropertyType(
            UUID id,
            FullQualifiedName fqn,
            String title,
            Optional<String> description,
            Set<FullQualifiedName> schemas,
            EdmPrimitiveTypeKind datatype ) {
        this( Optional.of( id ),
                fqn,
                title,
                description,
                schemas,
                datatype,
                Optional.empty(),
                Optional.empty(),
                Optional.empty() );
    }

    public PropertyType(
            FullQualifiedName fqn,
            String title,
            Optional<String> description,
            Set<FullQualifiedName> schemas,
            EdmPrimitiveTypeKind datatype ) {
        this( Optional.empty(),
                fqn,
                title,
                description,
                schemas,
                datatype,
                Optional.empty(),
                Optional.empty(),
                Optional.empty() );
    }

    @JsonProperty( SerializationConstants.DATATYPE_FIELD )
    public EdmPrimitiveTypeKind getDatatype() {
        return datatype;
    }

    @JsonProperty( SerializationConstants.PII_FIELD )
    public boolean isPIIfield() {
        return piiField;
    }

    @JsonProperty( SerializationConstants.MULTI_VALUED )
    public boolean isMultiValued() {
        return multiValued;
    }

    @JsonProperty( SerializationConstants.ANALYZER )
    public Analyzer getAnalyzer() {
        return analyzer;
    }

    @JsonIgnore
    public void setPii( boolean pii ) {
        this.piiField = pii;
    }

    @Override public boolean equals( Object o ) {
        if ( this == o ) { return true; }
        if ( !( o instanceof PropertyType ) ) { return false; }
        if ( !super.equals( o ) ) { return false; }
        PropertyType that = (PropertyType) o;
        return multiValued == that.multiValued &&
                piiField == that.piiField &&
                datatype == that.datatype &&
                analyzer == that.analyzer;
    }

    @Override public int hashCode() {
        if ( h == 0 ) {
            return h = Objects.hash( super.hashCode(), multiValued, datatype, piiField, analyzer );
        } else {
            return h;
        }
    }

    @Override
    public String toString() {
        return "PropertyType [datatype=" + datatype + ", piiField=" + piiField + ", analyzer=" + analyzer + ", schemas="
                + schemas + ", type=" + type + ", id=" + id + ", title=" + title + ", description=" + description + "]";
    }

    @Override
    @JsonIgnore
    public SecurableObjectType getCategory() {
        return SecurableObjectType.PropertyTypeInEntitySet;
    }
}
