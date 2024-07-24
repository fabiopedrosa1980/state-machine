package br.com.pedrosa.statemachine;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<OrdersStates, OrderEvents> {
    @Override
    public void configure(StateMachineStateConfigurer<OrdersStates, OrderEvents> states) throws Exception {
        states
                .withStates()
                .initial(OrdersStates.NEW)
                .states(EnumSet.allOf(OrdersStates.class))
                .end(OrdersStates.COMPLETED)
                .end(OrdersStates.CANCELED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrdersStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal().source(OrdersStates.NEW).target(OrdersStates.VALIDATED).event(OrderEvents.VALIDATE)
                .action(validateOrderAction())
                .and()
                .withExternal().source(OrdersStates.VALIDATED).target(OrdersStates.PAID).event(OrderEvents.PAY)
                .action(payOrderAction())
                .and()
                .withExternal().source(OrdersStates.PAID).target(OrdersStates.SHIPED).event(OrderEvents.SHIP)
                .action(shipOrderAction())
                .and()
                .withExternal().source(OrdersStates.SHIPED).target(OrdersStates.COMPLETED).event(OrderEvents.COMPLETE)
                .and()
                .withExternal().source(OrdersStates.VALIDATED).target(OrdersStates.CANCELED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrdersStates.PAID).target(OrdersStates.CANCELED).event(OrderEvents.CANCEL);

    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrdersStates, OrderEvents> configuration) throws Exception {
        configuration.withConfiguration().listener(stateMachinelistener());
    }

    @Bean
    StateMachineListener<OrdersStates, OrderEvents> stateMachinelistener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void transition(Transition<OrdersStates, OrderEvents> transition) {
                if (transition.getSource() != null)
                    System.out.println("Transition from " + transition.getSource().getId() +
                            " to " + transition.getTarget().getId());
            }
        };
    }


    @Bean
    Action<OrdersStates, OrderEvents> shipOrderAction() {
        return stateContext -> {
            System.out.println("Shipping Order");
        };
    }

    @Bean
    Action<OrdersStates, OrderEvents> payOrderAction() {
        return stateContext -> {
            System.out.println("Paying Order");
        };
    }

    @Bean
    Action<OrdersStates, OrderEvents> validateOrderAction() {
        return stateContext -> {
            System.out.println("Validating Order");
        };
    }

}
