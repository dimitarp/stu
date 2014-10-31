package com.seitenbau.stu.dsl.tickets;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class TicketTest
{
  @Test
  public void notSolved()
  {
    assertThat(Ticket.solved("")).isFalse();
    assertThat(Ticket.solved("SomeTicket")).isFalse();

    assertThat(Ticket.notSolved("")).isTrue();
    assertThat(Ticket.notSolved("SomeTicket")).isTrue();
  }

  @Test
  public void solved()
  {
    assertThat(Ticket.solved("fixed:x1")).isTrue();
    assertThat(Ticket.notSolved("fixed:x1")).isFalse();
  }

  @Test(expected = IllegalArgumentException.class)
  public void isNull()
  {
    Ticket.solved(null);
  }
}
