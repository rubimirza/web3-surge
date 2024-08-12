package org.apache.shardingsphere.infra.executor.sql.log;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.binder.QueryContext;
import org.apache.shardingsphere.infra.executor.sql.context.ExecutionContext;
import org.apache.shardingsphere.infra.executor.sql.context.ExecutionUnit;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * SQL logger.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j(topic = "NftMint-ShardingSphere-SQL")
public final class SQLLogger {

    /**
     * Log SQL.
     *
     * @param queryContext     query context
     * @param showSimple       whether show SQL in simple style
     * @param executionContext Execution context
     */
    public static void logSQL(final QueryContext queryContext, final boolean showSimple, final ExecutionContext executionContext) {
        // log("Logic SQL: {}", queryContext.getSql());
        // log("SQLStatement: {}", queryContext.getSqlStatementContext().getSqlStatement());
        if (showSimple) {
            logSimpleMode(executionContext.getExecutionUnits());
        } else {
            logNormalMode(executionContext.getExecutionUnits());
        }
    }

    private static void logSimpleMode(final Collection<ExecutionUnit> executionUnits) {
        Set<String> dataSourceNames = new HashSet<>(executionUnits.size());
        for (ExecutionUnit each : executionUnits) {
            dataSourceNames.add(each.getDataSourceName());
        }
        log("Actual SQL(simple): {} ::: {}", dataSourceNames, executionUnits.size());
    }

    private static void logNormalMode(final Collection<ExecutionUnit> executionUnits) {
        for (ExecutionUnit each : executionUnits) {
            if (each.getSqlUnit().getParameters().isEmpty()) {
                log("Actual SQL: {} ::: {}", each.getDataSourceName(), each.getSqlUnit().getSql());
            } else {
                log("Actual SQL: {} ::: {} ::: {}", each.getDataSourceName(), each.getSqlUnit().getSql(), each.getSqlUnit().getParameters());
            }
        }
    }

    private static void log(final String pattern, final Object... arguments) {
        log.info(pattern, arguments);
    }
}
