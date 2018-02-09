
import java.util.*;
import java.util.stream.Collectors;

import static FACT.FACT;

@javax.annotation.Generated(value = {"decision.ftl", "main"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "main",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Main extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "main",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Main() {
    }

    public java.math.BigDecimal apply(String n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((n != null ? number(n) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Main'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((n != null ? number(n) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Main'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(n, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            java.math.BigDecimal output_ = evaluate(n, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'main' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return FACT(n, annotationSet_, eventListener_, externalExecutor_);
    }
}
