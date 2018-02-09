/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.transformation.basic;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.transformation.java.CompoundStatement;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.omg.spec.dmn._20151101.dmn.*;

import javax.xml.bind.JAXBElement;

/**
 * Created by Octavian Patrascoiu on 13/06/2017.
 */
public class ContextToJavaTransformer {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final EnvironmentFactory environmentFactory;

    ContextToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
    }

    public Statement expressionToJava(TContext context, TDRGElement element) {
        Environment elementEnvironment = dmnTransformer.makeEnvironment(element);

        // Make context environment
        Environment contextEnvironment = dmnTransformer.makeContextEnvironment(context, elementEnvironment);
        return contextExpressionToJava(context, contextEnvironment, element);
    }

    Statement contextExpressionToJava(TContext context, Environment contextEnvironment, TDRGElement element) {
        // Translate to Java
        CompoundStatement statement = new CompoundStatement();
        ExpressionStatement returnValue = null;
        for(TContextEntry entry: context.getContextEntry()) {
            // Translate value
            ExpressionStatement value;
            Type entryType = dmnTransformer.entryType(entry, contextEnvironment);
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                value = (ExpressionStatement) dmnTransformer.expressionToJava(expression, contextEnvironment, element);
            } else {
                value = new ExpressionStatement("null", entryType);
            }

            // Add statement
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String javaName = dmnTransformer.lowerCaseFirst(variable.getName());
                String javaType = dmnTransformer.toJavaType(entryType);
                String assignmentText = String.format("%s %s = %s;", javaType, javaName, value.getExpression());
                statement.add(new ExpressionStatement(assignmentText, entryType));
            } else {
                returnValue = value;
            }
        }

        // Add return statement
        Type returnType = dmnTransformer.toFEELType(dmnTransformer.drgElementOutputTypeRef(element));
        if (returnValue != null) {
            String text = String.format("return %s;", returnValue.getExpression());
            statement.add(new ExpressionStatement(text, returnType));
        } else {
            // Make complex type value
            String complexJavaType = dmnTransformer.drgElementOutputType(element);
            String complexTypeVariable = dmnTransformer.drgElementVariableName(element);
            String expressionText = String.format("%s %s = %s;", dmnTransformer.itemDefinitionJavaClassName(complexJavaType), complexTypeVariable, dmnTransformer.defaultConstructor(dmnTransformer.itemDefinitionJavaClassName(complexJavaType)));
            statement.add(new ExpressionStatement(expressionText, returnType));
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                // Add statement
                TInformationItem variable = entry.getVariable();
                Type entryType = dmnTransformer.entryType(entry, contextEnvironment);
                if (variable != null) {
                    String javaContextEntryName = dmnTransformer.lowerCaseFirst(variable.getName());
                    String entryText = String.format("%s.%s(%s);", complexTypeVariable, dmnTransformer.setter(javaContextEntryName), javaContextEntryName);
                    statement.add(new ExpressionStatement(entryText, entryType));
                }
            }
            // Return value
            String returnText = String.format("return %s;", complexTypeVariable);
            statement.add(new ExpressionStatement(returnText, returnType));
        }
        return statement;
    }
}
