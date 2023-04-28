const changeDateTimeFormat = (date) => {
    if (date === null || date === undefined) {
      return "-";
    }
    const dateict = new Date(date);
  
    const dateString = dateict.toDateString();
    const hour = dateict.getHours();
    const minute = dateict.getMinutes();
    const formattedTime = `${hour < 10 ? "0" : ""}${hour}:${
      minute < 10 ? "0" : ""
    }${minute}`;
  
    const splitDate = dateString.split(" ");
    const dateFormat = `${splitDate[2]} ${splitDate[1]} ${splitDate[3]}, ${formattedTime}`;
    return dateFormat;
  };

export {changeDateTimeFormat}