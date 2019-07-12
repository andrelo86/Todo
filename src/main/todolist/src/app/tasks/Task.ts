export class Task {

  id: number;
  description: string;
  state: string;
  image: string;

  constructor(other: Partial<Task> = {}) {
    this.id = other.id;
    this.description = other.description;
    this.state = other.state;
    this.image = other.image;
  }

  public static fromPartial(other: Partial<Task>) {
    return new Task(other);
  }

}
