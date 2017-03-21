#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2016-2017 Testify Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package examples.resource.model;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * A greeting model.
 *
 * @author saden
 */
public class GreetingModel {

    private String phrase;

    public GreetingModel() {
    }

    public GreetingModel(String phrase) {
        this.phrase = phrase;
    }

    @SafeHtml
    @NotNull
    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.phrase);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GreetingModel other = (GreetingModel) obj;
        return Objects.equals(this.phrase, other.phrase);
    }

    @Override
    public String toString() {
        return "GreetingModel{" + "phrase=" + phrase + '}';
    }

}
