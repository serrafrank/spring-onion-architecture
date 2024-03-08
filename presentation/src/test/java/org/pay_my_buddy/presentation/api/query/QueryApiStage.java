package org.pay_my_buddy.presentation.api.query;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import lombok.Getter;
import org.pay_my_buddy.entity.commun.api.query.Query;
import org.pay_my_buddy.entity.commun.api.query.QueryHandler;
import org.pay_my_buddy.presentation.api.DefaultQueryApi;
import org.pay_my_buddy.presentation.api.providers.QueryHandlerProvider;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JGivenStage
public class QueryApiStage extends Stage<QueryApiStage> {

    private final List<QueryHandler<?, ?>> queryHandlers = new ArrayList<>();

    private DefaultQueryApi defaultQueryBus;

    private FirstQueryHandlerImpl firstQueryHandler = new FirstQueryHandlerImpl();
    private SecondQueryHandlerImpl secondQueryHandler = new SecondQueryHandlerImpl();


    private Query query = null;

    private Exception exception = null;

    @BeforeStage
    public void setUp() {
        queryHandlers.clear();
        resetBean();

        firstQueryHandler = new FirstQueryHandlerImpl();
        secondQueryHandler = new SecondQueryHandlerImpl();
        query = null;
        exception = null;
    }

    public QueryApiStage a_query() {
        this.query = new TestQuery();
        return self();
    }


    public QueryApiStage a_query_handler_for_the_query() {
        queryHandlers.add(firstQueryHandler);
        resetBean();
        return self();
    }

    public QueryApiStage two_querys_handler_for_the_same_query() {
        queryHandlers.addAll(List.of(firstQueryHandler, secondQueryHandler));
        resetBean();
        return self();
    }


    public QueryApiStage no_handler() {
        return self();
    }

    public QueryApiStage execute_the_query() {
        try {
            defaultQueryBus.request(query);
        } catch (Exception e) {
            this.exception = e;
        }
        return self();
    }

    public QueryApiStage the_query_is_executed() {
        assertThat(exception).isNull();
        assertThat(firstQueryHandler.isExecuted()).isTrue();
        return self();
    }

    private void resetBean() {
        try {
            final QueryHandlerProvider queryHandlerProvider = new QueryHandlerProvider(queryHandlers);
            defaultQueryBus = new DefaultQueryApi(queryHandlerProvider);
        } catch (Exception e) {
            this.exception = e;
        }
    }

    public void an_exception_is_thrown() {
        assertThat(exception).isNotNull();
    }

    private static class TestQuery implements Query<String> {
    }


    @Getter
    private static class FirstQueryHandlerImpl implements QueryHandler<TestQuery, String> {

        private boolean executed = false;

        @Override
        public String handle(TestQuery query) {
            executed = true;
            return "ok";
        }

        public void reset() {
            executed = false;
        }
    }

    @Getter
    private static class SecondQueryHandlerImpl implements QueryHandler<TestQuery, String> {

        private boolean executed = false;

        @Override
        public String handle(TestQuery query) {
            executed = true;
            return "ok";
        }

        public void reset() {
            executed = false;
        }
    }


}
